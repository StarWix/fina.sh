package sh.fina.commands;

import lombok.AllArgsConstructor;
import sh.fina.services.PriceService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.time.temporal.ChronoUnit;

@ShellComponent()
@AllArgsConstructor
public class PriceCommands {
    private final PriceService priceService;

    @ShellMethod(key = "pull prices")
    public void pull(final @ShellOption(defaultValue = "USD") String symbol,
                     final @ShellOption(defaultValue = "DAYS") ChronoUnit timeframe) {
        priceService.pull(symbol, timeframe);
    }
}
