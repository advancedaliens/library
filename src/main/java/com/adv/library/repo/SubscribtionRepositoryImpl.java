package com.adv.library.repo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.adv.library.model.Subscription;
import com.adv.library.model.SubscriptionDetail;
import com.adv.library.model.SubscriptionMapper;

@Repository
public class SubscribtionRepositoryImpl implements SubscribtionRepository {
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	private final String SQL_FIND_BOOK_INVENTORY_BY_ID = "select qty_available from sampledb.book_inventory where book_id=?";
	
	private final String SQL_FIND_BOOK_QUANTITY_BY_ID = "select quantity from sampledb.book where book_id=?";
	
	private final String SQL_ISSUE_BOOK_BY_BOOK_ID_USER_ID = 
			"insert into sampledb.subscriptions (book_id, user_id, borrowed_date, due_date) values (?,?,?,?)";
	
	private final String SQL_INSERT_BOOK_INVENTORY = 
			"insert into sampledb.book_inventory (book_id, qty_available) values (?,?)";
	
	private final String SQL_UPDATE_BOOK_INVENTORY = 
			"update sampledb.book_inventory set qty_available = ? where book_id = ?";
	
	private final String SQL_FIND_SUBSCRIPTION_TO_RETURN = "select user_id, book_id, borrowed_date, due_date, returned_date, penalty_paid from "
			+ "sampledb.subscriptions where book_id = ? and user_id = ? and returned_date is null";
	
	private final String SQL_UPDATE_SUBSCRIPTON = "update sampledb.subscriptions set returned_date=?, penalty_paid=? where book_id=? and user_id=? ";
	
	private final String SQL_UPDATE_BOOK_INVENTORY_FOR_RETURN = 
			"update sampledb.book_inventory set qty_available = qty_available + 1 where book_id = ?";
	
	private final String SQL_FIND_ALL_SUBSCRIBTION_DETAILS_BY_USER_ID = "select u.first_name, u.last_name, b.book_name, s.borrowed_date, "
			+ "s.due_date, s.returned_date, s.penalty_paid from sampledb.subscriptions s "
			+ "join sampledb.user u on u.user_id = s.user_id "
			+ "join sampledb.book b on b.book_id = s.book_id where s.user_id = ?";
	
	private final String SQL_FIND_ALL_SUBSCRIBTION_DETAILS_BY_USER_NAME = "select u.first_name, u.last_name, b.book_name, s.borrowed_date, "
			+ "s.due_date, s.returned_date, s.penalty_paid from sampledb.subscriptions s "
			+ "join sampledb.user u on u.user_id = s.user_id "
			+ "join sampledb.book b on b.book_id = s.book_id "
			+ "where lower(u.first_name) = ? and lower(last_name) = ?";
	
	private final String SQL_FIND_ALL_SUBSCRIBTION_DETAILS_BY_BOOK_ID = "select u.first_name, u.last_name, b.book_name, s.borrowed_date, "
			+ "s.due_date, s.returned_date, s.penalty_paid from sampledb.subscriptions s "
			+ "join sampledb.user u on u.user_id = s.user_id "
			+ "join sampledb.book b on b.book_id = s.book_id where b.book_id = ?";
	
	private final String SQL_FIND_ALL_SUBSCRIBTION_DETAILS_BY_BOOK_NAME = "select u.first_name, u.last_name, b.book_name, s.borrowed_date, "
			+ "s.due_date, s.returned_date, s.penalty_paid from sampledb.subscriptions s "
			+ "join sampledb.user u on u.user_id = s.user_id "
			+ "join sampledb.book b on b.book_id = s.book_id "
			+ "where lower(b.book_name) = ?";
	
	private static final Logger log = LoggerFactory.getLogger(SubscribtionRepositoryImpl.class);  
	
	@Value("${book.penalty.per.day}")
	private Double bookPenaltyPerDay;

	@Override
	public boolean borrowBook(Long bookId, Long userId) {
		
		log.debug("In borrowBook, bookId:{}, userId:{}", bookId, userId);
		
		if (isBookAvailable(bookId)) {
			
			LocalDateTime issueDate = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			LocalDateTime dueDate = issueDate.plusWeeks(2);
			
			jdbcTemplate.update(SQL_ISSUE_BOOK_BY_BOOK_ID_USER_ID, new Object[] {bookId, userId, issueDate, dueDate });
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean returnBook(Long bookId, Long userId) {
		
		log.debug("In returnBook, bookId:{}, userId:{}", bookId, userId);
		
		//check if the book is issued to the user and if a penalty needs to be paid
		Subscription subscription = null;
		double penalty = 0;
		boolean bookReturned = false;
		try {
			subscription = jdbcTemplate.queryForObject(SQL_FIND_SUBSCRIPTION_TO_RETURN, new Object[] {bookId, userId}, 
					new SubscriptionMapper());
		} catch (EmptyResultDataAccessException e) {
			System.out.println("The userId:" + userId  + " doesn't currently hold bookId:" + bookId);
		}
		
		if (subscription != null) {
				
			subscription.getDueDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			
			long overDueDays = java.time.temporal.ChronoUnit.DAYS.between(
					subscription.getDueDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now());
			
			log.debug("overDueDays:{}", overDueDays);
			
			if (overDueDays > 0) {
				penalty = bookPenaltyPerDay * overDueDays;
			}
			
			int numRowsUpdated = jdbcTemplate.update(SQL_UPDATE_SUBSCRIPTON, new Object[] {new Date(), penalty, bookId, userId});
			
			if (numRowsUpdated == 1) {
				
				numRowsUpdated = jdbcTemplate.update(SQL_UPDATE_BOOK_INVENTORY_FOR_RETURN, new Object[] {bookId});
				
				if (numRowsUpdated == 1) {
					bookReturned = true;
				} else {
					//may be we will have throw an exception and undo the transaction
				}
			}
		} 
		return bookReturned;
	}
	
	private boolean isBookAvailable(Long bookId) {
		
		Long inventory = null;
		boolean inventoryRowNotPresent = false;
		
		log.debug("In isBookAvailable, bookId:{}", bookId);
		
		try {
			inventory = jdbcTemplate.queryForObject(SQL_FIND_BOOK_INVENTORY_BY_ID, new Object[] { bookId }, Long.class);
		} catch (EmptyResultDataAccessException e) {
			inventoryRowNotPresent = true;
		}	
		
		if (inventoryRowNotPresent) {
			log.debug("inventoryRowNotPresent, so inserting the row");
			Long bookQuantity = null;
			try {
				bookQuantity = jdbcTemplate.queryForObject(SQL_FIND_BOOK_QUANTITY_BY_ID, new Object[] { bookId }, Long.class);
			} catch (EmptyResultDataAccessException e) {
				log.error("Book not available with id{}:", bookId);
			}	
			
			if (bookQuantity != null) {
				jdbcTemplate.update(SQL_INSERT_BOOK_INVENTORY, new Object[] {bookId, (bookQuantity - 1)});
				return true;
			} else {
				log.error("Book not available with id{}:",bookId);
				return false;
			}
		}
		
		if (inventory != null && inventory == 0) {
			log.error("Inventory is 0 for bookId:{}", bookId);
			return false;
		} else {
			jdbcTemplate.update(SQL_UPDATE_BOOK_INVENTORY, new Object[] {(inventory -1) , bookId});
		}
		
		return true;
	}

	@Override
	public List<SubscriptionDetail> getAllSubscriptionsByUserId(Long userId) {
		log.debug("In getAllSubscriptionsByUserId method");

        List<SubscriptionDetail> result = jdbcTemplate.query(
        		SQL_FIND_ALL_SUBSCRIBTION_DETAILS_BY_USER_ID, new Object[] {userId}, 
                (rs, rowNum) -> new SubscriptionDetail(rs.getString("first_name"), rs.getString("last_name"), rs.getString("book_name"),
                		rs.getTimestamp("borrowed_date"), rs.getTimestamp("due_date"), rs.getTimestamp("returned_date"), rs.getDouble("penalty_paid"))
        		);

        return result;
	}

	@Override
	public List<SubscriptionDetail> getAllSubscriptionsByUserName(String firstName, String lastName) {
		log.debug("In getAllSubscriptionsByUserName method");

        List<SubscriptionDetail> result = jdbcTemplate.query(
        		SQL_FIND_ALL_SUBSCRIBTION_DETAILS_BY_USER_NAME, new Object[] {firstName, lastName}, 
                (rs, rowNum) -> new SubscriptionDetail(rs.getString("first_name"), rs.getString("last_name"), rs.getString("book_name"),
                		rs.getTimestamp("borrowed_date"), rs.getTimestamp("due_date"), rs.getTimestamp("returned_date"), rs.getDouble("penalty_paid"))
        		);

        return result;
	}

	@Override
	public List<SubscriptionDetail> getAllSubscriptionsByBookId(Long bookId) {
		log.debug("In getAllSubscriptionsByBookId method");

        List<SubscriptionDetail> result = jdbcTemplate.query(
        		SQL_FIND_ALL_SUBSCRIBTION_DETAILS_BY_BOOK_ID, new Object[] {bookId}, 
                (rs, rowNum) -> new SubscriptionDetail(rs.getString("first_name"), rs.getString("last_name"), rs.getString("book_name"),
                		rs.getTimestamp("borrowed_date"), rs.getTimestamp("due_date"), rs.getTimestamp("returned_date"), rs.getDouble("penalty_paid"))
        		);

        return result;
	}

	@Override
	public List<SubscriptionDetail> getAllSubscriptionsByBookName(String bookName) {
		log.debug("In getAllSubscriptionsByBookName method");

        List<SubscriptionDetail> result = jdbcTemplate.query(
        		SQL_FIND_ALL_SUBSCRIBTION_DETAILS_BY_BOOK_NAME, new Object[] {bookName}, 
                (rs, rowNum) -> new SubscriptionDetail(rs.getString("first_name"), rs.getString("last_name"), rs.getString("book_name"),
                		rs.getTimestamp("borrowed_date"), rs.getTimestamp("due_date"), rs.getTimestamp("returned_date"), rs.getDouble("penalty_paid"))
        		);

        return result;
	}
}
