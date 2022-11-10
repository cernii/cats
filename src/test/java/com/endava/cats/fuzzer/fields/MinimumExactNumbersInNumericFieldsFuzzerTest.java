package com.endava.cats.fuzzer.fields;

import com.endava.cats.args.FilesArguments;
import com.endava.cats.http.HttpMethod;
import com.endava.cats.http.ResponseCodeFamily;
import com.endava.cats.model.FuzzingData;
import io.quarkus.test.junit.QuarkusTest;
import io.swagger.v3.oas.models.media.StringSchema;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Collections;

@QuarkusTest
class MinimumExactNumbersInNumericFieldsFuzzerTest {
    private MinimumExactNumbersInNumericFieldsFuzzer minimumExactNumbersInNumericFieldsFuzzer;

    private FilesArguments filesArguments;

    @BeforeEach
    void setup() {
        filesArguments = Mockito.mock(FilesArguments.class);
        minimumExactNumbersInNumericFieldsFuzzer = new MinimumExactNumbersInNumericFieldsFuzzer(null, null, null, filesArguments);
    }

    @Test
    void givenANewStringFieldsRightBoundaryFuzzer_whenCreatingANewInstance_thenTheMethodsBeingOverriddenAreMatchingTheStringFieldsRightBoundaryFuzzer() {
        StringSchema stringSchema = new StringSchema();
        FuzzingData data = FuzzingData.builder().requestPropertyTypes(Collections.singletonMap("test", stringSchema)).build();
        Mockito.when(filesArguments.getRefData(Mockito.anyString())).thenReturn(Collections.emptyMap());
        Assertions.assertThat(minimumExactNumbersInNumericFieldsFuzzer.getSchemaTypesTheFuzzerWillApplyTo()).containsOnly("integer", "number");
        Assertions.assertThat(minimumExactNumbersInNumericFieldsFuzzer.hasBoundaryDefined("test", data)).isFalse();
        Assertions.assertThat(minimumExactNumbersInNumericFieldsFuzzer.description()).isNotNull();
        Assertions.assertThat(minimumExactNumbersInNumericFieldsFuzzer.getExpectedHttpCodeWhenOptionalFieldsAreFuzzed()).isEqualByComparingTo(ResponseCodeFamily.TWOXX);
        Assertions.assertThat(minimumExactNumbersInNumericFieldsFuzzer.getExpectedHttpCodeWhenRequiredFieldsAreFuzzed()).isEqualByComparingTo(ResponseCodeFamily.TWOXX);
        Assertions.assertThat(minimumExactNumbersInNumericFieldsFuzzer.typeOfDataSentToTheService()).isEqualTo("exact minimum size values");
        Assertions.assertThat(minimumExactNumbersInNumericFieldsFuzzer.skipForHttpMethods()).containsOnly(HttpMethod.GET, HttpMethod.DELETE);

        stringSchema.setMinimum(BigDecimal.TEN);
        Assertions.assertThat(minimumExactNumbersInNumericFieldsFuzzer.hasBoundaryDefined("test", data)).isTrue();
        Assertions.assertThat(minimumExactNumbersInNumericFieldsFuzzer.getBoundaryValue(stringSchema)).isNotNull();
    }
}