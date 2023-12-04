package labb5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Main {

    Kattio io;

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        io = new Kattio(System.in, System.out);

        readRoleAssignmentProblem();

        solveRoleAssignmentProblem(n, s, k, actorsInRoles, roleInScenesMap);

        printResult(true);

        io.close();

    }

    /*
     * 
     * #TASK:
     * # Implement any heuristic that solves the construction problem:
     * # Which actors should have which roles in order to solve the role assignment
     * problem with as few actors as possible?
     * # Indata format:
     * # First line: integer N, number of roles
     * # Second line: integer S, number of scenes
     * # Third line: integer K, number of actors
     * # Next N lines: integer P, number of possible actors for a role, followed by
     * S integers, the actors that can play the role
     * # Next S lines: integer Q, number of roles in a scene, followed by Q
     * integers, the roles in the scene
     * 
     * # Output format:
     * # First line: integer M, number of actors that are assigned a role
     * # Following lines: integer R, the actor, integer T, the amount of roles the
     * actor is assigned, T integers, the roles the actor is assigned
     * 
     * # Requirements:
     * # Divas must have at least one role in at least one scene, they cannot be in
     * the same scene as each other
     * # No role can be played by more than one actor
     * # No actor can play more than one role in the same scene
     */

    int n, s, k, highestAmountOfActorsAvailableForRole, totalNumberOfActors;
    int[][] actorsInRoles, rolesInScenes, assignedRoles;
    int[] actorsAvailableForRole;
    HashMap<Integer, List<Integer>> roleInScenesMap;
    List<Integer> diva1CanPlay, diva2CanPlay;

    public void readRoleAssignmentProblem() {
        // # of roles
        n = io.getInt();
        // # of scenes
        s = io.getInt();
        // # of actors
        k = io.getInt();

        actorsAvailableForRole = new int[k];

        actorsInRoles = new int[n][k];

        diva1CanPlay = new ArrayList<>();
        diva2CanPlay = new ArrayList<>();

        // iterate over all roles
        for (int i = 0; i < n; i++) {
            // # of actors that can play this role
            int p = io.getInt();
            for (int j = 0; j < p; j++) {

                int actor = io.getInt();

                if (actor == 1) {
                    diva1CanPlay.add(i);
                } else if (actor == 2) {
                    diva2CanPlay.add(i);
                }

                actorsInRoles[i][j] = actor;
            }
        }
        roleInScenesMap = new HashMap<>();

        for (int i = 0; i < s; i++) {
            int q = io.getInt();
            for (int j = 0; j < q; j++) {
                int role = io.getInt() - 1;
                if (!roleInScenesMap.containsKey(role)) {
                    // This number has not been seen before, create a new list
                    List<Integer> lines = new ArrayList<>();
                    lines.add(i + 1); // Assuming line numbers start from 1
                    roleInScenesMap.put(role, lines);
                } else {
                    // This number is already in the map, add the current line to its list
                    roleInScenesMap.get(role).add(i + 1);
                }
            }
        }

        // Create some datastructure that you can input a role and get the scene(s) that
        // it is in

        // If you try to assign more than x actors and they can't be assigned to that
        // particular role, then assign superactor
    }

    public void solveRoleAssignmentProblem(int roles, int scenes, int actors, int[][] actorsForRoles,
            HashMap<Integer, List<Integer>> roleInScenesMap) {

        /*
         * We assign the divas to the first roles that they can have (not the same role,
         * not in the same scene)
         * We categorize each role by how hard it is to fill (how few actors can play
         * it)
         * We assign the roles with the fewest possible actors first (for each new
         * assignment check that the actor is not already assigned to a role in the same
         * scene)
         */

        // There can be at mt n-1 superactors so total amount of posssible actors is
        // actors + n-1 (k + n - 1)
        assignedRoles = new int[actors + n - 1][roles];

        // Sort actorsForRoles by how many actors can play each role
        Arrays.sort(actorsForRoles, Comparator.comparingInt(a -> a.length));

        // Assign divas to first available role

        int superactorsCount = 0;

        boolean[] actorAlreadyAssigned = new boolean[actors];

        List<Integer> firstDivaRoles = diva1CanPlay.size() <= diva2CanPlay.size() ? diva1CanPlay : diva2CanPlay;
        List<Integer> secondDivaRoles = diva1CanPlay.size() > diva2CanPlay.size() ? diva1CanPlay : diva2CanPlay;

        outerLoop: for (Integer firstDivaRole : diva1CanPlay) {
            for (Integer secondDivaRole : diva2CanPlay) {

                List<Integer> scenesForFirstDivaRole = roleInScenesMap.get(firstDivaRole);
                List<Integer> scenesForSecondDivaRole = roleInScenesMap.get(secondDivaRole);

                boolean hasCommonScene = false;
                for (Integer scene : scenesForSecondDivaRole) {
                    if (scenesForFirstDivaRole.contains(scene)) {
                        hasCommonScene = true;
                        break;
                    }
                }

                if (!hasCommonScene) {
                    // They do not share any scene, assign them
                    assignedRoles[0][firstDivaRole] = 1;
                    assignedRoles[1][secondDivaRole] = 1;
                    totalNumberOfActors += 2;

                    break outerLoop;
                }
            }
        }

        // Assign roles with fewest possible actors first
        // Loop through list where i is the role and j is the actor
        rolesLoop: for (int i = 0; i < actorsForRoles.length; i++) {

            if (roleInScenesMap.get(i) == null) {
                // This role is not in any scene, skip it
                continue;
            }
            if (assignedRoles[0][i] == 1 || assignedRoles[1][i] == 1) {
                // Divas are already assigned to this role, skip it
                continue rolesLoop;
            }

            actorLoop: for (int j = 0; j < actorsForRoles[i].length; j++) {

                if (actorsForRoles[i][j] == 0) {

                    // Assign superactor, give them some actor number
                    assignedRoles[actors + superactorsCount][i] = 1;
                    superactorsCount++;
                    totalNumberOfActors++;
                    continue rolesLoop;
                }

                int actor = actorsForRoles[i][j] - 1;

                if (actor == 0 || actor == 1) {
                    continue actorLoop;
                }
                // Loop over all roles that the actor is assigned to
                for (int k = 0; k < assignedRoles[actor].length; k++) {

                    // Get the role that the actor is assigned to
                    int role = k;

                    if (assignedRoles[actor][role] == 0) {
                        // Dont have to check a role that the actor is not assigned to
                        continue;
                    }

                    // Retrieve lists of scenes for the current role and the role to assign
                    List<Integer> scenesForCurrentRole = roleInScenesMap.get(role);
                    List<Integer> scenesForNewRole = roleInScenesMap.get(i);

                    // Check if there is any scene overlap
                    for (Integer scene : scenesForNewRole) {
                        if (scenesForCurrentRole.contains(scene)) {
                            // The actor is already assigned to a role in the same scene, try the next actor
                            continue actorLoop;
                        }
                    }
                }

                // else assign the role to the actor/super actor
                assignedRoles[actor][i] = 1;

                if (!actorAlreadyAssigned[actor]) {
                    actorAlreadyAssigned[actor] = true;
                    totalNumberOfActors++;
                }

                continue rolesLoop;
            }

            // If we get here, no actor could be assigned to the role
            // Assign superactor, give them some actor number
            assignedRoles[actors + superactorsCount][i] = 1;
            superactorsCount++;
            totalNumberOfActors++;

        }
    }

    public int findIndex(int[][] array, int columnIndex) {
        for (int rowIndex = 0; rowIndex < array[columnIndex].length; rowIndex++) {
            if (array[columnIndex][rowIndex] == 1) {
                return rowIndex; // Return the first row index where the value is 1
            }
        }
        return -1;
    }

    public void printResult(boolean debug) {

        if (debug) {
            System.out.println(totalNumberOfActors);
            for (int i = 0; i < assignedRoles.length; i++) {

                int numRoles = 0;
                for (int j = 0; j < assignedRoles[i].length; j++) {
                    if (assignedRoles[i][j] == 1) {
                        numRoles++;
                    }
                }

                if (numRoles == 0) {
                    continue;
                }

                System.out.print(i + 1 + " " + numRoles + " ");

                for (int j = 0; j < assignedRoles[i].length; j++) {
                    if (assignedRoles[i][j] == 1) {
                        System.out.print(j + 1 + " ");
                    }
                }

                System.out.print("\n");
            }
        } else {

            io.println(totalNumberOfActors);
            for (int i = 0; i < assignedRoles.length; i++) {

                int numRoles = 0;
                for (int j = 0; j < assignedRoles[i].length; j++) {
                    if (assignedRoles[i][j] == 1) {
                        numRoles++;
                    }
                }

                if (numRoles == 0) {
                    continue;
                }

                io.print(i + 1 + " " + numRoles + " ");

                for (int j = 0; j < assignedRoles[i].length; j++) {
                    if (assignedRoles[i][j] == 1) {
                        io.print(j + 1 + " ");
                    }
                }

                io.print("\n");
            }
        }

    }

}