package com.visiblethread.docanalyzer.utils;

import java.util.Set;

public class Constants {

    public static final Set<String> EXCLUDED_WORDS = Set.of("the", "me", "you", "i", "of", "and", "a", "we");

    public static final String SAMPLE_DOCUMENT_TEXT =
            "The contract offer includes terms and conditions. I signed the contract with a letter. " +
                    "You and me agree to the offer. The agreement details payment terms of the contract.";
}
