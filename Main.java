import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

public class Main {
    public static void main (String[] args) {


        Scanner scanner = new Scanner (System.in);

        boolean gameStillGoing = true;

        int[] a1 = new int[1];
        int[] a2 = new int[1];
        int[] a3 = new int[1];

        int[] b1 = new int[1];
        int[] b2 = new int[1];
        int[] b3 = new int[1];

        int[] c1 = new int[1];
        int[] c2 = new int[1];
        int[] c3 = new int[1];

        int Player1 = 1;
        int Player2 = 2;

        boolean Player1Played = false;
        boolean Player2Played = true;
        boolean AI = false;
        boolean didHePlay = false;

        int NumberOfCaseFull = 0;

        System.out.println ("Bienvenue au jeu du morpion");
        System.out.println ("Voilà la grille :");
        System.out.println ("a1 | a2 | a3");
        System.out.println ("b1 | b2 | b3");
        System.out.println ("c1 | c2 | c3");
        System.out.println ("Souhaitez-vous jouer avec une IA ou un autre joueur?");
        String UserInput1 = scanner.nextLine ().toLowerCase ();

        if (UserInput1.equals ("ai")) {
            AI = true;
            System.out.println ("Mode IA activé");
        } else {
            AI = false;
        }



        int[][] Pattern = new int[9][];

        Pattern[0] = a1;
        Pattern[1] = a2;
        Pattern[2] = a3;

        Pattern[3] = b1;
        Pattern[4] = b2;
        Pattern[5] = b3;

        Pattern[6] = c1;
        Pattern[7] = c2;
        Pattern[8] = c3;

        // Utilisation d'un hash map, pour correspondre à l'input > case
        Map <String, int[]> board = new HashMap <> ();
        board.put("a1", a1);
        board.put("a2", a2);
        board.put("a3", a3);
        board.put("b1", b1);
        board.put("b2", b2);
        board.put("b3", b3);
        board.put("c1", c1);
        board.put("c2", c2);
        board.put("c3", c3);

        while (true) {
            if (gameStillGoing) {
                String PlayerThatShouldPlay = WhoIsPlaying (Player1Played,Player2Played);
                System.out.println ("Tapper votre choix de case");

                if (NumberOfCaseFull == 9) {
                    System.out.println ("Egalité!");
                    gameStillGoing = false;
                }

                if (PlayerThatShouldPlay.equals ("Player1")) {
                    System.out.println ("Au tour du joueur 1");
                    String UserInput = scanner.nextLine ();

                    System.out.println (NumberOfCaseFull);

                    didHePlay = InputToCase (UserInput,board,Pattern,Player1,NumberOfCaseFull);

                    if(DidSomeoneWin (board,Pattern,1,NumberOfCaseFull)) {
                        gameStillGoing = false;
                        System.out.println ("Joueur 1 à gagner la partie!");
                    }

                    if (didHePlay) {
                        NumberOfCaseFull++;
                        Player1Played = !Player1Played;
                        Player2Played = !Player2Played;
                    }
                }
                if (PlayerThatShouldPlay.equals("Player2") & 9 > NumberOfCaseFull) {
                    System.out.println ("Au tour du joueur 2");

                    if (AI & 9 > NumberOfCaseFull) {
                        didHePlay = AIToCase (board,Pattern,Player2,NumberOfCaseFull);
                    }


                    if (!AI) {
                        String UserInput = scanner.nextLine ();
                        didHePlay = InputToCase (UserInput,board,Pattern,Player2,NumberOfCaseFull);
                    }


                    if(DidSomeoneWin (board,Pattern,2,NumberOfCaseFull)) {
                        gameStillGoing = false;
                        System.out.println ("Joueur 2 à gagner la partie!");
                    }

                    if (didHePlay) {
                        NumberOfCaseFull++;
                        Player1Played = !Player1Played;
                        Player2Played = !Player2Played;
                    }
                }

                continue;
            } else {
                break;
            }
        }
    }

    public static boolean InputToCase (String input, Map<String, int[]> board, int[][] Pattern,int PlayerNumber,int NumberOfCaseFull) {
        if (board.containsKey (input)) {
            int[] selectedCase = board.get(input);

            if (!(selectedCase[0] == 0)) {
                System.out.println ("Impossible de le placer ici, la case est peut être déjà prise");
                return false;
            }
            selectedCase[0] = PlayerNumber; // Example: mark the position
            System.out.println("Marked position: " + input);
            System.out.println (Translation (Arrays.toString (board.get ("a1"))) + Translation (Arrays.toString (board.get ("a2"))) + Translation (Arrays.toString (board.get ("a3"))));
            System.out.println (Translation (Arrays.toString (board.get ("b1"))) + Translation (Arrays.toString (board.get ("b2"))) + Translation (Arrays.toString (board.get ("b3"))));
            System.out.println (Translation (Arrays.toString (board.get ("c1"))) + Translation (Arrays.toString (board.get ("c2"))) + Translation (Arrays.toString (board.get ("c3"))));
            return true;
        } else {
            System.out.println ("Cette case n'existe pas!");
            return false;
        }
    }

    public  static String randomCase() {
        int RandomInteger = (ThreadLocalRandom.current().nextInt(1, 9));

        String CaseSelected = "";

        if (RandomInteger == 1) {
            CaseSelected = "a1";
        }
        if (RandomInteger == 2) {
            CaseSelected = "a2";
        }
        if (RandomInteger == 3) {
            CaseSelected = "a3";
        }

        if (RandomInteger == 4) {
            CaseSelected = "b1";
        }
        if (RandomInteger == 5) {
            CaseSelected = "b2";
        }
        if (RandomInteger == 6) {
            CaseSelected = "b3";
        }

        if (RandomInteger == 7) {
            CaseSelected = "c1";
        }
        if (RandomInteger == 8) {
            CaseSelected = "c2";
        }
        if (RandomInteger == 9) {
            CaseSelected = "c3";
        }

        return CaseSelected;
    }

    public static String canWin (Map<String, int[]> board, int[][] Pattern,int PlayerNumber) {
        // C'est une fonction qui détermine si l'IA à une chance de gagner sur tout le plateau
        int[] a1 = board.get ("a1");
        int[] a2 = board.get ("a2");
        int[] a3 = board.get ("a3");

        int[] b1 = board.get ("b1");
        int[] b2 = board.get ("b2");
        int[] b3 = board.get ("b3");

        int[] c1 = board.get ("c1");
        int[] c2 = board.get ("c2");
        int[] c3 = board.get ("c3");

        String caseToPlay = "";


        //rangée a
        if (a1[0] == PlayerNumber & a2[0] == PlayerNumber & a3[0] == 0) { // cas ou 0 0
            caseToPlay = "a3";
        }
        if (a1[0] == PlayerNumber & a2[0] == 0 & a3[0] == PlayerNumber) { // cas ou 0  0
            caseToPlay = "a2";
        }
        if (a1[0] == 0 & a2[0] == PlayerNumber & a3[0] == PlayerNumber) { // cas ou  0 0
            caseToPlay = "a1";
        }
        //rangée b
        if (b1[0] == PlayerNumber & b2[0] == PlayerNumber & b3[0] == 0) {
            caseToPlay = "b3";
        }
        if (b1[0] == PlayerNumber & b2[0] == 0 & b3[0] == PlayerNumber) {
            caseToPlay = "b2";
        }
        if (b1[0] == 0 & b2[0] == PlayerNumber & b3[0] == PlayerNumber) {
            caseToPlay = "b1";
        }
        //rangée c
        if (c1[0] == PlayerNumber & c2[0] == PlayerNumber & c3[0] == 0) {
            caseToPlay = "c3";
        }
        if (c1[0] == PlayerNumber & c2[0] == 0 & c3[0] == PlayerNumber) {
            caseToPlay = "c2";
        }
        if (c1[0] == 0 & c2[0] == PlayerNumber & c3[0] == PlayerNumber) {
            caseToPlay = "c1";
        }



        //colonne 1
        if (a1[0] == PlayerNumber & b1[0] == PlayerNumber & c1[0] == 0) { // cas ou 0 0
            caseToPlay = "c1";
        }
        if (a1[0] == PlayerNumber & b1[0] == 0 & c1[0] == PlayerNumber) { // cas ou 0  0
            caseToPlay = "b1";
        }
        if (a1[0] == 0 & b1[0] == PlayerNumber & c1[0] == PlayerNumber) { // cas ou  0 0
            caseToPlay = "a1";
        }
        //colonne 2
        if (b2[0] == PlayerNumber & a2[0] == PlayerNumber & c2[0] == 0) {
            caseToPlay = "c2";
        }
        if (b2[0] == PlayerNumber & a2[0] == 0 & c2[0] == PlayerNumber) {
            caseToPlay = "a2";
        }
        if (b2[0] == 0 & a2[0] == PlayerNumber & c2[0] == PlayerNumber) {
            caseToPlay = "b2";
        }
        //colonne 3
        if (c3[0] == PlayerNumber & a3[0] == PlayerNumber & b3[0] == 0) {
            caseToPlay = "b3";
        }
        if (c3[0] == PlayerNumber & a3[0] == 0 & b3[0] == PlayerNumber) {
            caseToPlay = "a3";
        }
        if (c3[0] == 0 & a3[0] == PlayerNumber & b3[0] == PlayerNumber) {
            caseToPlay = "c3";
        }

        //croisée a1 -> c3
        if (a1[0] == PlayerNumber & b2[0] == PlayerNumber & c3[0] == 0) {
            caseToPlay = "c3";
        }
        if (a1[0] == PlayerNumber & b2[0] == 0 & c3[0] == PlayerNumber) {
            caseToPlay = "c3";
        }
        if (a1[0] == 0 & b2[0] == PlayerNumber & c3[0] == PlayerNumber) {
            caseToPlay = "a1";
        }

        //croisée a3 -> c1
        if (a3[0] == PlayerNumber & b2[0] == PlayerNumber & c1[0] == 0) {
            caseToPlay = "c1";
        }
        if (a3[0] == PlayerNumber & b2[0] == 0 & c1[0] == PlayerNumber) {
            caseToPlay = "b2";
        }
        if (a3[0] == 0 & b2[0] == PlayerNumber & c1[0] == PlayerNumber) {
            caseToPlay = "a3";
        }

        return caseToPlay;
    }

    public static String shouldDefend (Map<String, int[]> board, int[][] Pattern,int PlayerNumber) {
        // Fonction qui détermine si l'IA doit absolument défendre pour ne pas perdre.
        int[] a1 = board.get ("a1");
        int[] a2 = board.get ("a2");
        int[] a3 = board.get ("a3");

        int[] b1 = board.get ("b1");
        int[] b2 = board.get ("b2");
        int[] b3 = board.get ("b3");

        int[] c1 = board.get ("c1");
        int[] c2 = board.get ("c2");
        int[] c3 = board.get ("c3");

        String caseToPlay = "";


        //rangée a
        if (a1[0] == 1 & a2[0] == 1 & a3[0] == 0) { // cas ou 0 0
            caseToPlay = "a3";
        }
        if (a1[0] == 1 & a2[0] == 0 & a3[0] == 1) { // cas ou 0  0
            caseToPlay = "a2";
        }
        if (a1[0] == 0 & a2[0] == 1 & a3[0] == 1) { // cas ou  0 0
            caseToPlay = "a1";
        }
        //rangée b
        if (b1[0] == 1 & b2[0] == 1 & b3[0] == 0) {
            caseToPlay = "b3";
        }
        if (b1[0] == 1 & b2[0] == 0 & b3[0] == 1) {
            caseToPlay = "b2";
        }
        if (b1[0] == 0 & b2[0] == 1 & b3[0] == 1) {
            caseToPlay = "b1";
        }
        //rangée c
        if (c1[0] == 1 & c2[0] == 1 & c3[0] == 0) {
            caseToPlay = "c3";
        }
        if (c1[0] == 1 & c2[0] == 0 & c3[0] == 1) {
            caseToPlay = "c2";
        }
        if (c1[0] == 0 & c2[0] == 1 & c3[0] == 1) {
            caseToPlay = "c1";
        }



        //colonne 1
        if (a1[0] == 1 & b1[0] == 1 & c1[0] == 0) { // cas ou 0 0
            caseToPlay = "c1";
        }
        if (a1[0] == 1 & b1[0] == 0 & c1[0] == 1) { // cas ou 0  0
            caseToPlay = "b1";
        }
        if (a1[0] == 0 & b1[0] == 1 & c1[0] == 1) { // cas ou  0 0
            caseToPlay = "a1";
        }
        //colonne 2
        if (b2[0] == 1 & a2[0] == 1 & c2[0] == 0) {
            caseToPlay = "c2";
        }
        if (b2[0] == 1 & a2[0] == 0 & c2[0] == 1) {
            caseToPlay = "a2";
        }
        if (b2[0] == 0 & a2[0] == 1 & c2[0] == 1) {
            caseToPlay = "b2";
        }
        //colonne 3
        if (c3[0] == 1 & a3[0] == 1 & b3[0] == 0) {
            caseToPlay = "b3";
        }
        if (c3[0] == 1 & a3[0] == 0 & b3[0] == 1) {
            caseToPlay = "a3";
        }
        if (c3[0] == 0 & a3[0] == 1 & b3[0] == 1) {
            caseToPlay = "c3";
        }

        //croisée a1 -> c3
        if (a1[0] == 1 & b2[0] == 1 & c3[0] == 0) {
            caseToPlay = "c3";
        }
        if (a1[0] == 1 & b2[0] == 0 & c3[0] == 1) {
            caseToPlay = "c3";
        }
        if (a1[0] == 0 & b2[0] == 1 & c3[0] == 1) {
            caseToPlay = "a1";
        }

        //croisée a3 -> c1
        if (a3[0] == 1 & b2[0] == 1 & c1[0] == 0) {
            caseToPlay = "c1";
        }
        if (a3[0] == 1 & b2[0] == 0 & c1[0] == 1) {
            caseToPlay = "b2";
        }
        if (a3[0] == 0 & b2[0] == 1 & c1[0] == 1) {
            caseToPlay = "a3";
        }

        return caseToPlay;
    }

    public static Boolean AIToCase (Map<String, int[]> board, int[][] Pattern,int PlayerNumber,int NumberOfCaseFull) {

        String canWin = canWin (board,Pattern,PlayerNumber);
        String shouldDefend = shouldDefend (board,Pattern,PlayerNumber);


        // ici on attend pour faire plus naturel
        try {
            Thread.sleep(1000); // Pause de 2 secondes
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        if (!canWin.isEmpty ()) {
            int[] selectedCase = board.get (canWin);


            selectedCase[0] = PlayerNumber;
            System.out.println("Marked position: " + canWin);
            System.out.println (Translation (Arrays.toString (board.get ("a1"))) + Translation (Arrays.toString (board.get ("a2"))) + Translation (Arrays.toString (board.get ("a3"))));
            System.out.println (Translation (Arrays.toString (board.get ("b1"))) + Translation (Arrays.toString (board.get ("b2"))) + Translation (Arrays.toString (board.get ("b3"))));
            System.out.println (Translation (Arrays.toString (board.get ("c1"))) + Translation (Arrays.toString (board.get ("c2"))) + Translation (Arrays.toString (board.get ("c3"))));
            return true;
        }

        if (!shouldDefend.isEmpty ()) {
            int[] selectedCase = board.get (shouldDefend);


            selectedCase[0] = PlayerNumber;
            System.out.println("Marked position: " + shouldDefend);
            System.out.println (Translation (Arrays.toString (board.get ("a1"))) + Translation (Arrays.toString (board.get ("a2"))) + Translation (Arrays.toString (board.get ("a3"))));
            System.out.println (Translation (Arrays.toString (board.get ("b1"))) + Translation (Arrays.toString (board.get ("b2"))) + Translation (Arrays.toString (board.get ("b3"))));
            System.out.println (Translation (Arrays.toString (board.get ("c1"))) + Translation (Arrays.toString (board.get ("c2"))) + Translation (Arrays.toString (board.get ("c3"))));
            return true;
        }

        String CaseToTake = randomCase ();

        if (board.containsKey (CaseToTake)) {
            if (board.get (CaseToTake)[0] > 0) {
                System.out.println ("Case full");
                AIToCase (board,Pattern,PlayerNumber,NumberOfCaseFull);
            } else {
                int[] selectedCase = board.get (CaseToTake);


                selectedCase[0] = PlayerNumber;
                System.out.println("Marked position: " + CaseToTake);
                System.out.println (Translation (Arrays.toString (board.get ("a1"))) + Translation (Arrays.toString (board.get ("a2"))) + Translation (Arrays.toString (board.get ("a3"))));
                System.out.println (Translation (Arrays.toString (board.get ("b1"))) + Translation (Arrays.toString (board.get ("b2"))) + Translation (Arrays.toString (board.get ("b3"))));
                System.out.println (Translation (Arrays.toString (board.get ("c1"))) + Translation (Arrays.toString (board.get ("c2"))) + Translation (Arrays.toString (board.get ("c3"))));

            }
        }

        return true;
    }

    public  static boolean DidSomeoneWin(Map<String, int[]> board, int[][] Pattern, int PlayerPlaying,int NumberOfCaseFull) {
        // fonction qui vérifie sur toute les cases si quelqu'un à aligner trois signe
        boolean PlayerHasWon = false;

        int[] a1 = board.get ("a1");
        int[] a2 = board.get ("a2");
        int[] a3 = board.get ("a3");

        int[] b1 = board.get ("b1");
        int[] b2 = board.get ("b2");
        int[] b3 = board.get ("b3");

        int[] c1 = board.get ("c1");
        int[] c2 = board.get ("c2");
        int[] c3 = board.get ("c3");



        if (a1[0] == PlayerPlaying & a2[0] == PlayerPlaying & a3[0] == PlayerPlaying) {
            PlayerHasWon = true;
        }
        if (b1[0] == PlayerPlaying & b2[0] == PlayerPlaying & b3[0] == PlayerPlaying) {
            PlayerHasWon = true;
        }
        if (c1[0] == PlayerPlaying & c2[0] == PlayerPlaying & c3[0] == PlayerPlaying) {
            PlayerHasWon = true;
        }

        if (a1[0] == PlayerPlaying & b1[0] == PlayerPlaying & c1[0] == PlayerPlaying) {
            PlayerHasWon = true;
        }
        if (a2[0] == PlayerPlaying & b2[0] == PlayerPlaying & c2[0] == PlayerPlaying) {
            PlayerHasWon = true;
        }
        if (a3[0] == PlayerPlaying & b3[0] == PlayerPlaying & c3[0] == PlayerPlaying) {
            PlayerHasWon = true;
        }

        if (a1[0] == PlayerPlaying & b2[0] == PlayerPlaying & c3[0] == PlayerPlaying) {
            PlayerHasWon = true;
        }
        if (a3[0] == PlayerPlaying & b2[0] == PlayerPlaying & c1[0] == PlayerPlaying) {
            PlayerHasWon = true;
        }

        return PlayerHasWon;
    }




    public static String WhoIsPlaying(boolean Player1Played,boolean Player2Played) {
        if (Player1Played) {
            return "Player2";
        } else if (Player2Played) {
            return "Player1";
        }
        return "";
    }

    public static String Translation(String NumbertoConvert) {
        if (NumbertoConvert.equals ("[1]")) {
            return "[X]";
        }
        if (NumbertoConvert.equals ("[2]")) {
            return "[O]";
        }
    return "[ ]";
    }
}