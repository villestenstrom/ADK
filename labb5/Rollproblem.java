package labb5;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

// To run test:
// cat validTest.txt solution.txt | verifyLab5
/*
cd labb5
javac *.java
cd ..
java labb5.Rollproblem < labb5/testfall/sample33.in
 */

public class Rollproblem {

    Kattio io;

    int n, s, k, maxSuper;
    int superCount = 0;
    // Structuring data
    Map<Integer, List<Integer>> roleToActors = new HashMap<>();
    Map<Integer, List<Integer>> sceneToRoles = new HashMap<>();

    private boolean checkIfTokensLeft() {
        if (!io.hasMoreTokens()) {
            System.out.println("No more tokens available in the input.");
            return false; // Or throw an exception
        }
        return true;
    }

    public void graphColoring(boolean debug) {
        /*
         *
         * LABB 4:
         *
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
         * - Each actor can only play one role in each scene
         * - Every role must be present in at least one scene
         * Additional:
         * - The divas p1 and p2 are guaranteed at least one role each
         * - Those roles are guaranteed to be in at least one scene each
         * - They can't be in the same scene
         * 
         * LABB 5:
         * Same input format as above
         * 
         * New output format:
         * Number of cast actors
         * Actor1 number, number of roles, role number, role number, ...
         * Actor2 number, number of roles, role number, role number, ...
         * ...
         */

        if (!checkIfTokensLeft())
            return;
        int V = io.getInt();
        if (!checkIfTokensLeft())
            return;
        int E = io.getInt();
        if (!checkIfTokensLeft())
            return;
        int m = io.getInt();
        if (!checkIfTokensLeft())
            return;
        // number of roles
        n = V + 2;
        // number of scenes
        s = V + E + 2;
        // number of actors
        k = m + 2;
        maxSuper = n - 1;

        // Populate the HashMaps based on input data
        for (int i = 0; i < n; i++) {
            if (!checkIfTokensLeft())
                return;
            int numActors = io.getInt();
            List<Integer> actors = new ArrayList<>();
            for (int j = 0; j < numActors; j++) {
                if (!checkIfTokensLeft())
                    return;
                actors.add(io.getInt());
            }
            roleToActors.put(i, actors);
        }

        for (int i = 0; i < s; i++) {
            if (!checkIfTokensLeft())
                return;
            int numRoles = io.getInt();
            List<Integer> roles = new ArrayList<>();
            for (int j = 0; j < numRoles; j++) {
                if (!checkIfTokensLeft())
                    return;
                roles.add(io.getInt());
            }
            sceneToRoles.put(i, roles);
        }

        // Assign roles
        Map<Integer, List<Integer>> actorAssignments = assignRoles(roleToActors, sceneToRoles);
    }

    private Map<Integer, List<Integer>> assignRoles(Map<Integer, List<Integer>> roleToActors,
            Map<Integer, List<Integer>> sceneToRoles) {
        Map<Integer, List<Integer>> assignments = new HashMap<>();
        int[] assignedActors = new int[n]; // Tracks assigned actor for each role
        boolean[] actorUsed = new boolean[k + maxSuper]; // Tracks whether an actor (or super actor) has been used

        // Sortera roller baserat på antalet tillgängliga skådespelare (stigande)
        List<Integer> sortedRoles = new ArrayList<>(roleToActors.keySet());
        sortedRoles.sort(Comparator.comparingInt(role -> roleToActors.get(role).size()));

        // Tilldela roller enligt den sorterade ordningen
        for (int role : sortedRoles) {
            List<Integer> eligibleActors = new ArrayList<>(roleToActors.get(role));
            int assignedActor = -1;

            // Hitta en skådespelare som inte leder till konflikter
            for (int actor : eligibleActors) {
                if (canAssignActor(actor, role, assignedActors, sceneToRoles)) {
                    assignedActor = actor;
                    break;
                }
            }

            if (assignedActor == -1) {
                // Använd en superskådespelare om ingen vanlig skådespelare hittas
                assignedActor = assignSuperActor(assignedActors, actorUsed, k, maxSuper);
            }

            assignedActors[role] = assignedActor;
            actorUsed[assignedActor] = true;
        }

        // Convert assigned actors to the required format
        for (int role = 0; role < n; role++) {
            int actor = assignedActors[role];
            if (!assignments.containsKey(actor)) {
                assignments.put(actor, new ArrayList<>());
            }
            assignments.get(actor).add(role);
        }

        return assignments;
    }

    private boolean canAssignActor(int actor, int role, int[] assignedActors,
            Map<Integer, List<Integer>> sceneToRoles) {
        for (Map.Entry<Integer, List<Integer>> entry : sceneToRoles.entrySet()) {
            if (entry.getValue().contains(role)) {
                // Kolla om aktören redan har en roll i denna scen
                for (int otherRole : entry.getValue()) {
                    if (otherRole != role && assignedActors[otherRole] == actor) {
                        // Konflikt: samma skådespelare i flera roller i samma scen
                        return false;
                    }
                }
            }
        }
        return true; // Inga konflikter hittades
    }

    private int assignSuperActor(int[] assignedActors, boolean[] actorUsed, int k, int maxSuper) {
        for (int i = k; i < k + maxSuper; i++) {
            if (!actorUsed[i]) {
                // Hittade en ledig superskådespelare
                return i;
            }
        }
        throw new IllegalStateException("Ran out of super actors");
    }

    private void printSolution(Map<Integer, List<Integer>> actorAssignments) {
        try {
            FileWriter fileWriter = new FileWriter("solution.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Count the total number of cast actors
            int totalActors = actorAssignments.size();
            bufferedWriter.write(String.valueOf(totalActors));
            bufferedWriter.newLine();

            // Write the details of each actor's assignments
            for (Map.Entry<Integer, List<Integer>> entry : actorAssignments.entrySet()) {
                StringBuilder sb = new StringBuilder();
                sb.append(entry.getKey()); // Actor number
                for (Integer role : entry.getValue()) {
                    sb.append(" ").append(role); // Append each role
                }
                bufferedWriter.write(sb.toString());
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Rollproblem() {
        io = new Kattio(System.in, System.out);

        graphColoring(true);

        // Move printSolution call here
        Map<Integer, List<Integer>> actorAssignments = assignRoles(roleToActors, sceneToRoles);
        printSolution(actorAssignments);

        io.close();
    }

    public static void main(String[] args) {
        new Rollproblem();
    }
}
