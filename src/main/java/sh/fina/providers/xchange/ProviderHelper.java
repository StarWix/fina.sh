package sh.fina.providers.xchange;

import sh.fina.models.ReadonlyProviderConfig;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;

public class ProviderHelper {
    public static ExchangeSpecification toExchangeSpecification(final ReadonlyProviderConfig config,
                                                                Class<? extends Exchange> exchange) {
        final var exchangeSpecification = new ExchangeSpecification(exchange);
        exchangeSpecification.setApiKey(config.getApiKey());
        exchangeSpecification.setSecretKey(config.getSecretKey());
        return exchangeSpecification;
    }
}
