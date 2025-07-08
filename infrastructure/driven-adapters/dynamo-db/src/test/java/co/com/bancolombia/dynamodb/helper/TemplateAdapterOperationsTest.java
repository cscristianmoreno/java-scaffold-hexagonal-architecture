package co.com.bancolombia.dynamodb.helper;

import co.com.bancolombia.dynamodb.DynamoDBTemplateAdapter;
import co.com.bancolombia.dynamodb.StatsEntity;
import co.com.bancolombia.model.stats.Stats;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.reactivecommons.utils.ObjectMapper;
import reactor.test.StepVerifier;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class TemplateAdapterOperationsTest {

    @Mock
    private DynamoDbEnhancedAsyncClient dynamoDbEnhancedAsyncClient;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private DynamoDbAsyncTable<StatsEntity> customerTable;

    private StatsEntity modelEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(dynamoDbEnhancedAsyncClient.table("stats_table", TableSchema.fromBean(StatsEntity.class)))
                .thenReturn(customerTable);

        modelEntity = new StatsEntity();

    }

    @Test
    void testSave() {
        when(customerTable.putItem(modelEntity)).thenReturn(CompletableFuture.runAsync(()->{}));
        when(mapper.map(modelEntity, StatsEntity.class)).thenReturn(modelEntity);

        DynamoDBTemplateAdapter dynamoDBTemplateAdapter =
                new DynamoDBTemplateAdapter(dynamoDbEnhancedAsyncClient, mapper);

        Stats stats = mapper.map(modelEntity, Stats.class);

        StepVerifier.create(dynamoDBTemplateAdapter.save(stats))
                .expectNextCount(1)
                .verifyComplete();
    }
}