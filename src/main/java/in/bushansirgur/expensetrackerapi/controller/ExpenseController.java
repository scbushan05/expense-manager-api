package in.bushansirgur.expensetrackerapi.controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import in.bushansirgur.expensetrackerapi.dto.CategoryDTO;
import in.bushansirgur.expensetrackerapi.dto.ExpenseDTO;
import in.bushansirgur.expensetrackerapi.io.CategoryResponse;
import in.bushansirgur.expensetrackerapi.io.ExpenseRequest;
import in.bushansirgur.expensetrackerapi.io.ExpenseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import in.bushansirgur.expensetrackerapi.entity.Expense;
import in.bushansirgur.expensetrackerapi.service.ExpenseService;
import jakarta.validation.Valid;

@RestController
public class ExpenseController {

	@Autowired
	private ExpenseService expenseService;
	
	@GetMapping("/expenses")
	public List<ExpenseResponse> getAllExpenses(Pageable page) {
		List<ExpenseDTO> listOfExpenses = expenseService.getAllExpenses(page);
		return listOfExpenses.stream().map(expenseDTO -> mapToResponse(expenseDTO)).collect(Collectors.toList());
	}
	
	@GetMapping("/expenses/{expenseId}")
	public ExpenseResponse getExpenseById(@PathVariable String expenseId){

		ExpenseDTO expenseDTO = expenseService.getExpenseById(expenseId);
		return mapToResponse(expenseDTO);
	}
	
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@DeleteMapping("/expenses")
	public void deleteExpenseById(@RequestParam String expenseId) {
		expenseService.deleteExpenseById(expenseId);
	}
	
	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping("/expenses")
	public ExpenseResponse saveExpenseDetails(@Valid @RequestBody ExpenseRequest expenseRequest) {
		ExpenseDTO expenseDTO = mapToDTO(expenseRequest);
		expenseDTO = expenseService.saveExpenseDetails(expenseDTO);
		return mapToResponse(expenseDTO);
	}

	private ExpenseResponse mapToResponse(ExpenseDTO expenseDTO) {
		return ExpenseResponse.builder()
				.expenseId(expenseDTO.getExpenseId())
				.name(expenseDTO.getName())
				.description(expenseDTO.getDescription())
				.amount(expenseDTO.getAmount())
				.date(expenseDTO.getDate())
				.category(mapToCategoryResponse(expenseDTO.getCategoryDTO()))
				.createdAt(expenseDTO.getCreatedAt())
				.updatedAt(expenseDTO.getUpdatedAt())
				.build();
	}

	private CategoryResponse mapToCategoryResponse(CategoryDTO categoryDTO) {
		return CategoryResponse.builder()
				.categoryId(categoryDTO.getCategoryId())
				.name(categoryDTO.getName())
				.build();
	}

	private ExpenseDTO mapToDTO(ExpenseRequest expenseRequest) {
		return ExpenseDTO.builder()
				.name(expenseRequest.getName())
				.description(expenseRequest.getDescription())
				.amount(expenseRequest.getAmount())
				.date(expenseRequest.getDate())
				.categoryId(expenseRequest.getCategoryId())
				.build();
	}

	@PutMapping("/expenses/{expenseId}")
	public ExpenseResponse updateExpenseDetails(@RequestBody ExpenseRequest expenseRequest, @PathVariable String expenseId){
		ExpenseDTO updatedExpense = mapToDTO(expenseRequest);
		updatedExpense = expenseService.updateExpenseDetails(expenseId, updatedExpense);
		return mapToResponse(updatedExpense);
	}
	
	@GetMapping("/expenses/category")
	public List<ExpenseResponse> getExpensesByCategory(@RequestParam String category, Pageable page) {
		List<ExpenseDTO> list = expenseService.readByCategory(category, page);
		return list.stream().map(expenseDTO -> mapToResponse(expenseDTO)).collect(Collectors.toList());
	}
	
	@GetMapping("/expenses/name")
	public List<ExpenseResponse> getExpensesByName(@RequestParam String keyword, Pageable page) {
		List<ExpenseDTO> list = expenseService.readByName(keyword, page);
		return list.stream().map(expenseDTO -> mapToResponse(expenseDTO)).collect(Collectors.toList());
	}
	
	@GetMapping("/expenses/date")
	public List<ExpenseResponse> getExpensesByDates(@RequestParam(required = false) Date startDate,
											@RequestParam(required = false) Date endDate,
											Pageable page) {
		List<ExpenseDTO> list = expenseService.readByDate(startDate, endDate, page);
		return list.stream().map(expenseDTO -> mapToResponse(expenseDTO)).collect(Collectors.toList());
	}
}






















