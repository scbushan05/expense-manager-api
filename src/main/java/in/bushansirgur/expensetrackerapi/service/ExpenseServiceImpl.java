package in.bushansirgur.expensetrackerapi.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import in.bushansirgur.expensetrackerapi.dto.CategoryDTO;
import in.bushansirgur.expensetrackerapi.dto.ExpenseDTO;
import in.bushansirgur.expensetrackerapi.entity.CategoryEntity;
import in.bushansirgur.expensetrackerapi.mappers.ExpenseMapper;
import in.bushansirgur.expensetrackerapi.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import in.bushansirgur.expensetrackerapi.entity.Expense;
import in.bushansirgur.expensetrackerapi.exceptions.ResourceNotFoundException;
import in.bushansirgur.expensetrackerapi.repository.ExpenseRepository;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {


	private final ExpenseRepository expenseRepo;
	private final UserService userService;
	private final CategoryRepository categoryRepository;

	private final ExpenseMapper expenseMapper;
	
	@Override
	public List<ExpenseDTO> getAllExpenses(Pageable page) {
		List<Expense> listOfExpenses = expenseRepo.findByUserId(userService.getLoggedInUser().getId(), page).toList();
		return listOfExpenses.stream().map(expense -> expenseMapper.mapToExpenseDTO(expense)).collect(Collectors.toList());
	}

	@Override
	public ExpenseDTO getExpenseById(String expenseId){
		Expense existingExpense = getExpenseEntity(expenseId);
		return expenseMapper.mapToExpenseDTO(existingExpense);

	}

	private Expense getExpenseEntity(String expenseId) {
		Optional<Expense> expense = expenseRepo.findByUserIdAndExpenseId(userService.getLoggedInUser().getId(), expenseId);
		if (!expense.isPresent()) {
			throw new ResourceNotFoundException("Expense is not found for the id "+ expenseId);
		}
		return expense.get();
	}

	@Override
	public void deleteExpenseById(String expenseId) {
		Expense expense = getExpenseEntity(expenseId);
		expenseRepo.delete(expense);
	}

	@Override
	public ExpenseDTO saveExpenseDetails(ExpenseDTO expenseDTO) {
		//check the existance of category
		Optional<CategoryEntity> optionalCategory = categoryRepository.findByUserIdAndCategoryId(userService.getLoggedInUser().getId(), expenseDTO.getCategoryId());
		if (!optionalCategory.isPresent()){
			throw new ResourceNotFoundException("Category not found for the id "+expenseDTO.getCategoryId());
		}
		expenseDTO.setExpenseId(UUID.randomUUID().toString());
		//map to entity object
		Expense newExpense = expenseMapper.mapToExpenseEntity(expenseDTO);
		//save to the database
		newExpense.setCategory(optionalCategory.get());
		newExpense.setUser(userService.getLoggedInUser());
		newExpense = expenseRepo.save(newExpense);
		//map to response object
		return expenseMapper.mapToExpenseDTO(newExpense);
	}

	@Override
	public ExpenseDTO updateExpenseDetails(String expenseId, ExpenseDTO expenseDTO){
		Expense existingExpense = getExpenseEntity(expenseId);
		if (expenseDTO.getCategoryId() != null) {
			Optional<CategoryEntity> optionalCategory = categoryRepository.findByUserIdAndCategoryId(userService.getLoggedInUser().getId(), expenseDTO.getCategoryId());
			if (!optionalCategory.isPresent()) {
				throw new ResourceNotFoundException("Category not found for the id "+expenseDTO.getCategoryId());
			}
			existingExpense.setCategory(optionalCategory.get());
		}
		existingExpense.setName(expenseDTO.getName() != null ? expenseDTO.getName() : existingExpense.getName());
		existingExpense.setDescription(expenseDTO.getDescription() != null ? expenseDTO.getDescription() : existingExpense.getDescription());
		existingExpense.setDate(expenseDTO.getDate() != null ? expenseDTO.getDate() : existingExpense.getDate());
		existingExpense.setAmount(expenseDTO.getAmount() != null ? expenseDTO.getAmount() : existingExpense.getAmount());
		existingExpense = expenseRepo.save(existingExpense);
		return expenseMapper.mapToExpenseDTO(existingExpense);
	}

	@Override
	public List<ExpenseDTO> readByCategory(String category, Pageable page) {
		Optional<CategoryEntity> optionalCategory = categoryRepository.findByNameAndUserId(category, userService.getLoggedInUser().getId());
		if (!optionalCategory.isPresent()) {
			throw new ResourceNotFoundException("Category not found for the name "+category);
		}
		List<Expense> list = expenseRepo.findByUserIdAndCategoryId(userService.getLoggedInUser().getId(), optionalCategory.get().getId(), page).toList();
		return list.stream().map(expense -> expenseMapper.mapToExpenseDTO(expense)).collect(Collectors.toList());
	}

	@Override
	public List<ExpenseDTO> readByName(String keyword, Pageable page) {
		List<Expense> list = expenseRepo.findByUserIdAndNameContaining(userService.getLoggedInUser().getId(), keyword, page).toList();
		return list.stream().map(expense -> expenseMapper.mapToExpenseDTO(expense)).collect(Collectors.toList());
	}

	@Override
	public List<ExpenseDTO> readByDate(Date startDate, Date endDate, Pageable page) {
		
		if (startDate == null) {
			startDate = new Date(0);
		}
		
		if (endDate == null) {
			endDate = new Date(System.currentTimeMillis());
		}
		
		List<Expense> list = expenseRepo.findByUserIdAndDateBetween(userService.getLoggedInUser().getId(), startDate, endDate, page).toList();
		return list.stream().map(expense -> expenseMapper.mapToExpenseDTO(expense)).collect(Collectors.toList());
	}

}



























