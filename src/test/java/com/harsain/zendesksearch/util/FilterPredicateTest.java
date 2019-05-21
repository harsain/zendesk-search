package com.harsain.zendesksearch.util;

import com.harsain.zendesksearch.dto.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class FilterPredicateTest {
    FilterPredicate filterPredicate = new FilterPredicate();

    @DisplayName("FilterPredicate.getGenericPredicate() with valid key")
    @Test
    public void testFilterPredicate() {

        User user = new User();
        user.set_id("1");

        boolean result = filterPredicate.getGenericPredicate("_id", "1").test(user);
        Assertions.assertTrue(result);
    }

    @DisplayName("FilterPredicate.getGenericPredicate() with invalid key")
    @Test
    public void testFilterPredicateInvalidKey() {

        User user = new User();
        user.set_id("1");

        boolean result = filterPredicate.getGenericPredicate("id", "1").test(user);
        Assertions.assertFalse(result);
    }

    @DisplayName("FilterPredicate.getGenericPredicate() with invalid value")
    @Test
    public void testFilterPredicateInvalidValue() {

        User user = new User();
        user.set_id("1");

        boolean result = filterPredicate.getGenericPredicate("_id", "1000").test(user);
        Assertions.assertFalse(result);
    }

    @DisplayName("FilterPredicate.getGenericPredicate() key with list type")
    @Test
    public void testFilterPredicateKeyWithListType() {

        User user = new User();
        user.set_id("1");
        user.setTags(new ArrayList<>(Arrays.asList("test1", "test2")));

        boolean result = filterPredicate.getGenericPredicate("tags", "test1").test(user);
        Assertions.assertTrue(result);
    }

    @DisplayName("FilterPredicate.getGenericPredicate() key with list type and invalid value")
    @Test
    public void testFilterPredicateKeyWithListTypeAndInvalidValue() {

        User user = new User();
        user.set_id("1");
        user.setTags(new ArrayList<>(Arrays.asList("test1", "test2")));

        boolean result = filterPredicate.getGenericPredicate("tags", "INVALID").test(user);
        Assertions.assertFalse(result);
    }

    @DisplayName("FilterPredicate.getGenericPredicate() key with list type and valid value case")
    @Test
    public void testFilterPredicateKeyWithListTypeAndValidValueCase() {

        User user = new User();
        user.set_id("1");
        user.setTags(new ArrayList<>(Arrays.asList("test1", "test2")));

        boolean result = filterPredicate.getGenericPredicate("tags", "Test1").test(user);
        Assertions.assertTrue(result);
    }
}
