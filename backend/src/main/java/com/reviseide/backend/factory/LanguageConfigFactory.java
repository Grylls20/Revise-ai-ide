package com.reviseide.backend.factory;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class LanguageConfigFactory {

    private static final Map<Integer, String> LANGUAGE_MAP = new HashMap<>();

    static {
        LANGUAGE_MAP.put(71, "python");
        LANGUAGE_MAP.put(62, "java");
        LANGUAGE_MAP.put(54, "cpp");
        LANGUAGE_MAP.put(63, "javascript");
        LANGUAGE_MAP.put(60, "go");
        LANGUAGE_MAP.put(73, "rust");
        LANGUAGE_MAP.put(72, "ruby");
    }

    public String getLanguageName(int languageId) {
        return LANGUAGE_MAP.getOrDefault(languageId, "unknown");
    }

    // This can be expanded to return specific compiler flags or environment variables per language
    public Map<String, String> getCompilerOptions(int languageId) {
        Map<String, String> options = new HashMap<>();
        if (languageId == 54) { // C++
            options.put("compiler_options", "-std=c++17 -O2");
        } else if (languageId == 62) { // Java
            options.put("compiler_options", "-Xlint:unchecked");
        }
        return options;
    }
}
