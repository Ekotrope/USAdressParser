package com.skovalenko.geocoder.address_parser.test;

import static com.skovalenko.geocoder.address_parser.us.UsAddressParserDataStrings.USPS_STREET_TYPE;
import static org.junit.Assert.assertEquals;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

import com.skovalenko.geocoder.address_parser.us.UsAddressParserDataStrings;

public class DataStringsTest {


    @Test
    public void testNoRemappings() {
        testMapping(UsAddressParserDataStrings.DIRECTION);
        testMapping(UsAddressParserDataStrings.SU_TYPES);
        testMapping(USPS_STREET_TYPE);
    }

    private void testMapping(Map<String, String> mappings) {
        Set<String> invalidRemappings = mappings.values()
            .stream()
            .filter(value ->
                    {
                        String mapping = mappings.get(value);
                        return !value.equals(mapping);
                    })
                .map(value -> "Remapping: (\"" + value + "\", \"" + mappings.get(value) + "\")")
                .collect(Collectors.toSet());

        Set<String> containWhitespace = mappings.entrySet()
                .stream()
                .filter(entry ->!(
                            entry.getKey().trim().equals(entry.getKey()) &&
                            entry.getValue().trim().equals(entry.getValue())
                    ))
                .map(
                        entry -> "Contain Whitespace: (\"" + entry.getKey() + "\", \"" + entry.getValue() + "\")"
                )
                .collect(Collectors.toSet());
        invalidRemappings.forEach( System.out::println);
        containWhitespace.forEach(System.out::println);
        assertEquals(0, invalidRemappings.size());
        assertEquals(0, containWhitespace.size());

    }
}
