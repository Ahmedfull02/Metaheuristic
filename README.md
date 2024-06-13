## Local search

Local search is a heuristic method for solving optimization problems. It iteratively explores the solution space by moving from the current solution to a neighboring solution, with the goal of finding an improved solution. Here's a basic pseudo code for a local search algorithm:


# Here the pseudo code for _Local search_:

    current_solution ← initial_solution
    best_solution ← current_solution
    best_objective_value ← objective_function(best_solution)
    
    iteration ← 0   
    
    while iteration < max_iterations do ## or some precised time
        neighbor_solution ← neighborhood_function(current_solution)
        neighbor_objective_value ← objective_function(neighbor_solution)

        if neighbor_objective_value < best_objective_value then
            best_solution ← neighbor_solution
            best_objective_value ← neighbor_objective_value
        end if

        current_solution ← neighbor_solution
        iteration ← iteration + 1
    end while  
    return best_solution


