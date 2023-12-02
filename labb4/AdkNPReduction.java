package labb4;

public class AdkNPReduction {

    Kattio io;

    int n, s, k;

    public void graphColoring(boolean debug) {
        /*
         * Out format:
         * # Roles (n)
         * # Scenes (s)
         * # Actors (k)
         * Next # n lines:
         * # Actors | # All Actors that can play role i
         * ...
         * Next # s lines:
         * # Roles | # All roles in scene i
         * ...
         * 
         * Requirements:
         * - Same actor can play multiple roles
         * - Same role can only be played by one actor
         * - Every role must be present in at least one scene
         * Additional:
         * - The divas p1 and p2 are guaranteed at least one role each
         * - Those roles are guaranteed to be in at least one scene each
         * - They can't be in the same scene
         */

        
        int V = io.getInt();
        int E = io.getInt();
        int m = io.getInt();
        n = V + 2;
        s = V + E + 2;
        k = m + 2;

        io.println(n);
        io.println(s);
        io.println(k);

        // actorsInRoles
        // each new line is a new role
        // each int is an actor that can play that role
        io.println("1 1");
        io.println("1 2");

        int x = k > n ? n : k;

        String actorsInRole = (x - 2) + " ";

        for (int i = 3; i < x + 1; i++) {
            actorsInRole += i + " ";
        }

        for (int i = 3; i < n + 1; i++) {
            io.println(actorsInRole);
        }

        // rolesInScenes
        // each new line is a new scene
        // each int is a role in that scene
        io.println("2 1 3");
        io.println("2 2 3");

        for (int i = 3; i < n + 1; i++) {
            io.println("2 1 " + i);
        }


        for (int i = 0; i < E; i++) {

            int from, to;

            from = io.getInt();
            to = io.getInt();

            io.println("2 " + (from + 2) + " " + (to + 2));
        }
    }

    AdkNPReduction() {
        io = new Kattio(System.in, System.out);

        graphColoring(false);

        io.close();
    }

    public static void main(String[] args) {
        new AdkNPReduction();
    }
}
