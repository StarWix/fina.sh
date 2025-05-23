package sh.fina.providers.blockscout;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import sh.fina.external.blockscount.client.api.DefaultApi;
import sh.fina.models.ReadonlyProviderConfig;

import java.util.Objects;

@Slf4j
public class IndirectTokenTransfersFetcher extends AbstractFetcher {
    public IndirectTokenTransfersFetcher(final ReadonlyProviderConfig config, final DefaultApi api) {
        super(config, api);
    }

    @Override
    public String getType() {
        return "indirect-token-transfers";
    }

    @Override
    public TransactionList<TransactionsFetcher.Meta> findTransactions(@Nullable TransactionsFetcher.Meta meta) {
        final var nextPageParams = meta == null ? null : meta.getNextPageParams();
        final var blockchainTransfers = api.getAddressTokenTransfers(publicKey, "to", null, null, nextPageParams);

        final var tokenTransfers = blockchainTransfers.getItems().stream()
                .map(tokenTransfer -> api.getTx(tokenTransfer.getTransactionHash()))
                .filter(transaction -> !transaction.getFrom().getHash().equals(publicKey)
                        && !transaction.getTo().getHash().equals(publicKey))
                .map(this::convert)
                .filter(Objects::nonNull)
                .toList();

        return new TransactionList<>(
                tokenTransfers,
                blockchainTransfers.getNextPageParams() == null ? null : new TransactionsFetcher.Meta(blockchainTransfers.getNextPageParams())
        );
    }
}
