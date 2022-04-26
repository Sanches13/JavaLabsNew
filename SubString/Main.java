import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        String source_1 = "113311113311111133133";
        String sample_1_1 = "12";
        String sample_1_2 = "33";

        String source_2 = "211111111111211111111112";
        String sample_2_1 = "2";

        String source_3 = "text1 text2 text3 text3 text3 text4 text4 text5 text3";
        String sample_3_1 = "text3";
        String sample_3_2 = "text";

        String source_4 = "11111";
        String sample_4_1 = "111111";
        String sample_4_2 = "11111";
        String sample_4_3 = "";

        String source_5 = "abaacbbabaabaa";
        String sample_5_1 = "abaa";

        Path path = Paths.get("bigText.txt");
        byte[] bytes = null;
        bytes = Files.readAllBytes(path);
        String source_6 = new String(bytes);
        String sample_6_1 = "что";

        System.out.println("_________________________Test №1_________________________");
        System.out.println("Source string: " + source_1);
        System.out.println("Sample: " + sample_1_1);
        System.out.println("Expected result: []");
        System.out.println("Rabin-Karp algorithm: " + SubString.RabinKarp(source_1, sample_1_1));
        System.out.println("Boyer-Moore algorithm: " + SubString.BoyerMoore(source_1, sample_1_1));
        System.out.println("Knuth-Morris-Pratt algorithm: " + SubString.KnuthMorrisPratt(source_1, sample_1_1));
        System.out.println("Algorithm based on finite state machine: " + SubString.RabinKarp(source_1, sample_1_1));
        System.out.println("_________________________________________________________");

        System.out.println("_________________________Test №2_________________________");
        System.out.println("Source string: " + source_1);
        System.out.println("Sample: " + sample_1_2);
        System.out.println("Expected result: [2, 8, 16, 19]");
        System.out.println("Rabin-Karp algorithm: " + SubString.RabinKarp(source_1, sample_1_2));
        System.out.println("Boyer-Moore algorithm: " + SubString.BoyerMoore(source_1, sample_1_2));
        System.out.println("Knuth-Morris-Pratt algorithm: " + SubString.KnuthMorrisPratt(source_1, sample_1_2));
        System.out.println("Algorithm based on finite state machine: " + SubString.RabinKarp(source_1, sample_1_2));
        System.out.println("_________________________________________________________");

        System.out.println("_________________________Test №3_________________________");
        System.out.println("Source string: " + source_2);
        System.out.println("Sample: " + sample_2_1);
        System.out.println("Expected result: [0, 12, 23]");
        System.out.println("Rabin-Karp algorithm: " + SubString.RabinKarp(source_2, sample_2_1));
        System.out.println("Boyer-Moore algorithm: " + SubString.BoyerMoore(source_2, sample_2_1));
        System.out.println("Knuth-Morris-Pratt algorithm: " + SubString.KnuthMorrisPratt(source_2, sample_2_1));
        System.out.println("Algorithm based on finite state machine: " + SubString.RabinKarp(source_2, sample_2_1));
        System.out.println("_________________________________________________________");

        System.out.println("_________________________Test №4_________________________");
        System.out.println("Source string: " + source_3);
        System.out.println("Sample: " + sample_3_1);
        System.out.println("Expected result: [12, 18, 24, 48]");
        System.out.println("Rabin-Karp algorithm: " + SubString.RabinKarp(source_3, sample_3_1));
        System.out.println("Boyer-Moore algorithm: " + SubString.BoyerMoore(source_3, sample_3_1));
        System.out.println("Knuth-Morris-Pratt algorithm: " + SubString.KnuthMorrisPratt(source_3, sample_3_1));
        System.out.println("Algorithm based on finite state machine: " + SubString.RabinKarp(source_3, sample_3_1));
        System.out.println("_________________________________________________________");

        System.out.println("_________________________Test №5_________________________");
        System.out.println("Source string: " + source_3);
        System.out.println("Sample: " + sample_3_2);
        System.out.println("Expected result: [0, 6, 12, 18, 24, 30, 36, 42, 48]");
        System.out.println("Rabin-Karp algorithm: " + SubString.RabinKarp(source_3, sample_3_2));
        System.out.println("Boyer-Moore algorithm: " + SubString.BoyerMoore(source_3, sample_3_2));
        System.out.println("Knuth-Morris-Pratt algorithm: " + SubString.KnuthMorrisPratt(source_3, sample_3_2));
        System.out.println("Algorithm based on finite state machine: " + SubString.RabinKarp(source_3, sample_3_2));
        System.out.println("_________________________________________________________");

        System.out.println("_________________________Test №6_________________________");
        System.out.println("Source string: " + source_4);
        System.out.println("Sample: " + sample_4_1);
        System.out.println("Expected result: []");
        System.out.println("Rabin-Karp algorithm: " + SubString.RabinKarp(source_4, sample_4_1));
        System.out.println("Boyer-Moore algorithm: " + SubString.BoyerMoore(source_4, sample_4_1));
        System.out.println("Knuth-Morris-Pratt algorithm: " + SubString.KnuthMorrisPratt(source_4, sample_4_1));
        System.out.println("Algorithm based on finite state machine: " + SubString.RabinKarp(source_4, sample_4_1));
        System.out.println("_________________________________________________________");

        System.out.println("_________________________Test №7_________________________");
        System.out.println("Source string: " + source_4);
        System.out.println("Sample: " + sample_4_2);
        System.out.println("Expected result: [0]");
        System.out.println("Rabin-Karp algorithm: " + SubString.RabinKarp(source_4, sample_4_2));
        System.out.println("Boyer-Moore algorithm: " + SubString.BoyerMoore(source_4, sample_4_2));
        System.out.println("Knuth-Morris-Pratt algorithm: " + SubString.KnuthMorrisPratt(source_4, sample_4_2));
        System.out.println("Algorithm based on finite state machine: " + SubString.RabinKarp(source_4, sample_4_2));
        System.out.println("_________________________________________________________");

        System.out.println("_________________________Test №8_________________________");
        System.out.println("Source string: " + source_4);
        System.out.println("Sample: " + sample_4_3);
        System.out.println("Expected result: []");
        System.out.println("Rabin-Karp algorithm: " + SubString.RabinKarp(source_4, sample_4_3));
        System.out.println("Boyer-Moore algorithm: " + SubString.BoyerMoore(source_4, sample_4_3));
        System.out.println("Knuth-Morris-Pratt algorithm: " + SubString.KnuthMorrisPratt(source_4, sample_4_3));
        System.out.println("Algorithm based on finite state machine: " + SubString.RabinKarp(source_4, sample_4_3));
        System.out.println("_________________________________________________________");

        System.out.println("_________________________Test №9_________________________");
        System.out.println("Source string: " + source_5);
        System.out.println("Sample: " + sample_5_1);
        System.out.println("Expected result: [0, 7, 10]");
        System.out.println("Rabin-Karp algorithm: " + SubString.RabinKarp(source_5, sample_5_1));
        System.out.println("Boyer-Moore algorithm: " + SubString.BoyerMoore(source_5, sample_5_1));
        System.out.println("Knuth-Morris-Pratt algorithm: " + SubString.KnuthMorrisPratt(source_5, sample_5_1));
        System.out.println("Algorithm based on finite state machine: " + SubString.RabinKarp(source_5, sample_5_1));
        System.out.println("_________________________________________________________");

        System.out.println("_________________________Test №10________________________");
        System.out.println("The source string is taken from bigText.java");
        System.out.println("Sample: " + sample_6_1);
        System.out.println("Expected result: 339 matches");
        System.out.println("Rabin-Karp algorithm: " + SubString.RabinKarp(source_6, sample_6_1).size() + " matches found");
        System.out.println("Boyer-Moore algorithm: " + SubString.BoyerMoore(source_6, sample_6_1).size() + " matches found");
        System.out.println("Knuth-Morris-Pratt algorithm: " + SubString.KnuthMorrisPratt(source_6, sample_6_1).size() + " matches found");
        System.out.println("Algorithm based on finite state machine: " + SubString.RabinKarp(source_6, sample_6_1).size() + " matches found");
        System.out.println("_________________________________________________________");

        System.out.println("_______________________Speed test________________________");
        System.out.println("The source string is taken from bigText.java");
        System.out.println("Sample: " + sample_6_1);

        System.out.print("Rabin-Karp algorithm: ");
        long start = System.currentTimeMillis();
        SubString.RabinKarp(source_6, sample_6_1);
        long finish = System.currentTimeMillis();
        System.out.println((finish - start) + " ms");

        System.out.print("Boyer-Moore algorithm: ");
        start = System.currentTimeMillis();
        SubString.BoyerMoore(source_6, sample_6_1);
        finish = System.currentTimeMillis();
        System.out.println((finish - start) + " ms");

        System.out.print("Knuth-Morris-Pratt algorithm: ");
        start = System.currentTimeMillis();
        SubString.KnuthMorrisPratt(source_6, sample_6_1);
        finish = System.currentTimeMillis();
        System.out.println((finish - start) + " ms");

        System.out.print("Algorithm based on finite state machine: ");
        start = System.currentTimeMillis();
        SubString.FiniteStateMachine(source_6, sample_6_1);
        finish = System.currentTimeMillis();
        System.out.println((finish - start) + " ms");

        System.out.print("The simplest algorithm: ");
        start = System.currentTimeMillis();
        SubString.simplestSearch(source_6, sample_6_1);
        finish = System.currentTimeMillis();
        System.out.println((finish - start) + " ms");
        System.out.println("_________________________________________________________");
    }
}
