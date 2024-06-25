package in.bushansirgur.expensetrackerapi.io;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExpenseRequest {

    @NotBlank(message = "Expense name must not be null")
    @Size(min = 3, message = "Expense name must be atleast 3 characters")
    private String name;

    private String description;

    @NotNull(message = "Expense amount should not be null")
    private BigDecimal amount;

    @NotBlank(message = "Category should not be null")
    private String categoryId;

    @NotNull(message = "Date must not be null")
    private Date date;
}
