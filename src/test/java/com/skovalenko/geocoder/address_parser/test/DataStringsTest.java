package com.skovalenko.geocoder.address_parser.test;

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
        testMapping(UsAddressParserDataStrings.STREET_NAMES);
        testMapping(UsAddressParserDataStrings.STREET_TYPE);
        testMapping(UsAddressParserDataStrings.USPS_STREET_TYPE);
    }

    @Test
    public void testNoMappingMismatch()
    {
        Set<String> strings = UsAddressParserDataStrings.STREET_TYPE
                .entrySet()
                .stream()
                .filter(entry ->
                {
                    String mapping = UsAddressParserDataStrings.USPS_STREET_TYPE.get(entry.getKey());
                    return mapping != null && !mapping.equals(entry.getValue());
                })
                .map(entry ->
                        String.format(
                                "Mapping Mismatch: Key %s maps to %s but should map to %s",
                                        entry.getKey(),
                                        entry.getValue(),
                                UsAddressParserDataStrings.USPS_STREET_TYPE.get(entry.getKey())
                                )
                )
                .collect(Collectors.toSet());
        strings.forEach( System.out::println);
        assertEquals(0, strings.size());
    }

    private void testMapping(Map<String, String> mappings) {
        Set<String> strings = mappings.values()
            .stream()
            .filter(value ->
                    {
                        String mapping = mappings.get(value);
                        return mapping != null && !mapping.equals(value);
                    })
                .map(value -> "Remapping: (\"" + value + "\", \"" + mappings.get(value) + "\")")
                .collect(Collectors.toSet());
        strings.forEach( System.out::println);
        assertEquals(0, strings.size());
    }
}
