package com.artificial.intelligence.utility;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Utilities {


    public static final Map<String, String> promptChain = new LinkedHashMap<>();

    static {
        // Step 1: Assess Topic
        promptChain.put("Assess Topic",
                "You are a senior Java technical interviewer.\n" +
                        "Analyze the topic: \"{{topic}}\".\n" +
                        "1. Summarize what this topic covers in the context of a Java technical interview.\n" +
                        "2. List 3–5 subtopics usually asked.\n" +
                        "3. Generate advanced topics for 12 years of experience.\n" +
                        "Return your output in this format:\n" +
                        "Topic Summary:\n- ...\nKey Subtopics:\n- ...\nDifficulty Level:\n- ...");

        // Step 2: Generate Interview Questions
        promptChain.put("Generate Interview Questions",
                "You are a senior Java interviewer.\n" +
                        "Generate 4–6 interview questions for the given topic.\n" +
                        "Include conceptual explanations of 2-3 lines.\n" +
                        "Format them as a numbered list.");

        // Step 3: Generate Answers
        promptChain.put("Generate Answers",
                "You are an expert Java mentor.\n" +
                        "Provide short, technically correct answers to each of the questions below.\n" +
                        "Format the answers with question numbers.");

        // Step 4: Generate Simple Code
        promptChain.put("Generate Code Examples",
                "You are an expert Java developer.\n" +
                        "Provide simple and clean Java code examples (if applicable) for the following interview questions.\n" +
                        "Only use Java, performance efficient code.");

        // Step 5: Summarize and Polish
        promptChain.put("Summarize and Polish",
                "You are a Java educator preparing revision notes.\n" +
                        "Summarize the key concepts in 4–6 bullet points for final interview revision.\n" +
                        "Include tips, best practices, and common mistakes if relevant.");
    }
    /*    Steps followed in the prompt chaining
            Step 1: Assess Topic
            Step 2: Generate Interview Questions
            Step 3: Generate Answers
            Step 4: Generate Simple Code
            Step 5: Summarize and Polish*/

    public static final Map<String, String> programPromptChain = new LinkedHashMap<>();

    static {
        // Step 1: Generate Coding Questions
        programPromptChain.put("Generate Coding Questions",
                "You are a Java coding interviewer.\n" +
                        "Generate 2–4 practical programming interview questions based on the topic: \"{{topic}}\".\n" +
                        "Focus on problem-solving, logic, and application of Java fundamentals.\n" +
                        "Format them as a numbered list.");

        // Step 2: Provide Code Solutions
        programPromptChain.put("Provide Code Solutions",
                "You are a senior Java developer.\n" +
                        "For each programming question below, write a clear and efficient Java solution.\n" +
                        "Include 2–3 lines of explanation below each solution explaining your approach.\n" +
                        "Stick to clean code principles.");

        // Step 3: Optimize and Review Code
        /*programPromptChain.put("Optimize and Review Code",
                "You are a Java code reviewer and performance expert.\n" +
                        "Suggest optimizations where possible if required");*/
    }

    public static Map<String, String> routes = Map.of(
            "conceptual",
            "You are a senior Java interviewer.\n" +
                    "Generate 4–6 conceptual Java interview questions based on the topic or query provided.\n" +
                    "For each question, add a 2–3 line explanation about what the interviewer is looking for.",

            "programming",
            "You are a hands-on Java coding interviewer.\n" +
                    "Generate 2–3 practical coding problems in Java based on the topic or feature mentioned.\n" +
                    "Keep problems clean, real-world focused, and suitable for 10+ years experience.",

            "general",
            "You are a Java interview coach.\n" +
                    "Help prepare the candidate by summarizing the topic and giving tips for interviews.\n" +
                    "Focus on structure, relevance, and career-level expectations."
    );

    public static List<String> subtopicsForParallel = List.of(
            "Subtopic: List and ArrayList in Java",
            "Subtopic: HashMap vs Hashtable",
            "Subtopic: Set and TreeSet behavior",
            "Subtopic: Concurrent Collections in Java",
            "Subtopic: Collections.sort() vs Stream.sorted()"
    );
}
