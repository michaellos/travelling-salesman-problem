import os
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

path_to_charts = "../out/charts/"
instances = ["berlin52", "pr76", "kroA100", "ch130", "ch150", "tsp225", "a280", "pcb442"]

def save_plot(plot, name, task, instance):

    if not os.path.exists(path_to_charts):
        os.mkdir(path_to_charts)
    if not os.path.exists(path_to_charts + task):
        os.mkdir(path_to_charts + task)
    if not os.path.exists(path_to_charts + task + instance):
        os.mkdir(path_to_charts + task + instance)
    plot.savefig(path_to_charts + task + instance + name)


def save_plot2(plot, name, task):

    if not os.path.exists(path_to_charts):
        os.mkdir(path_to_charts)
    if not os.path.exists(path_to_charts + task):
        os.mkdir(path_to_charts + task)
    plot.savefig(path_to_charts + task + name)


def task_2():
    output_folder = "Task 2/"

    average_results_greedy = []
    average_results_steepest = []
    average_results_random = []
    average_results_heuristics = []

    standard_deviation_greedy = []
    standard_deviation_steepest = []
    standard_deviation_random = []
    standard_deviation_heuristics = []

    best_results_greedy = []
    best_results_steepest = []
    best_results_random = []
    best_results_heuristics = []

    average_time_greedy = []
    average_time_steepest = []
    average_time_random = []
    average_time_heuristics = []

    efficiency_greedy = []
    efficiency_steepest = []
    efficiency_random = []
    efficiency_heuristics = []

    average_steps_greedy = []
    average_steps_steepest = []
    average_visit_solutions_greedy = []
    average_visit_solutions_steepest = []

    algorithm_names = []
    n_in_instances = [52, 76, 100, 130, 150, 225, 280, 442]

    for i in instances:
        data = pd.read_csv('../out/results_task_2_' + i + '.csv', sep=',')
        algorithm_names = data["Algorithm"].to_list()

        average_results_greedy.append(data["AverageResult"][0])
        average_results_steepest.append(data["AverageResult"][1])
        average_results_random.append(data["AverageResult"][2])
        average_results_heuristics.append(data["AverageResult"][3])

        standard_deviation_greedy.append(data["StandardDeviation"][0])
        standard_deviation_steepest.append(data["StandardDeviation"][1])
        standard_deviation_random.append(data["StandardDeviation"][2])
        standard_deviation_heuristics.append(data["StandardDeviation"][3])

        best_results_greedy.append(data["BestResult"][0])
        best_results_steepest.append(data["BestResult"][1])
        best_results_random.append(data["BestResult"][2])
        best_results_heuristics.append(data["BestResult"][3])

        average_time_greedy.append(data["AverageTime"][0])
        average_time_steepest.append(data["AverageTime"][1])
        average_time_random.append(data["AverageTime"][2])
        average_time_heuristics.append(data["AverageTime"][3])

        efficiency_greedy.append(data["Efficiency"][0])
        efficiency_steepest.append(data["Efficiency"][1])
        efficiency_random.append(data["Efficiency"][2])
        efficiency_heuristics.append(data["Efficiency"][3])

        average_steps_greedy.append(data["AverageStepNumber"][0])
        average_steps_steepest.append(data["AverageStepNumber"][1])

        average_visit_solutions_greedy.append(data["AverageVisitSolutionNumber"][0])
        average_visit_solutions_steepest.append(data["AverageVisitSolutionNumber"][1])

    plt.style.use('grayscale')
    fig1, ax1 = plt.subplots(figsize=(10, 6))
    plt.yscale("log")
    ax1.set_ylabel("Odległość od optimum - skala logarytmiczna")
    ax1.set_xlabel("Rozmiar instancji")
    ax1.errorbar(n_in_instances, average_results_greedy, standard_deviation_greedy, linestyle='solid', fmt='o')
    ax1.errorbar(n_in_instances, average_results_steepest, standard_deviation_steepest, linestyle='dashdot', fmt='D')
    ax1.errorbar(n_in_instances, average_results_random, standard_deviation_random, linestyle='dashed', fmt='^')
    ax1.errorbar(n_in_instances, average_results_heuristics, standard_deviation_heuristics, linestyle='dotted', fmt='x')
    plt.legend(algorithm_names)
    save_plot(plt, "AverageResult", output_folder, "/")
    plt.close(fig1)

    fig1, ax1 = plt.subplots(figsize=(10, 6))
    plt.yscale("log")
    ax1.plot(n_in_instances, best_results_greedy, linestyle='solid', markersize=4, marker='o')
    ax1.plot(n_in_instances, best_results_steepest, linestyle='dashdot', markersize=4, marker='D')
    ax1.plot(n_in_instances, best_results_random, linestyle='dashed', markersize=4, marker='^')
    ax1.plot(n_in_instances, best_results_heuristics, linestyle='dotted', markersize=4, marker='x')
    ax1.set_ylabel("Odległość od optimum - skala logarytmiczna")
    ax1.set_xlabel("Rozmiar instancji")
    plt.legend(algorithm_names)
    save_plot2(plt, "BestResult", output_folder)
    plt.close(fig1)

    fig1, ax1 = plt.subplots(figsize=(10, 6))
    ax1.plot(n_in_instances, average_time_greedy, linestyle='solid', markersize=4, marker='o')
    ax1.plot(n_in_instances, average_time_steepest, linestyle='dashdot', markersize=4, marker='D')
    ax1.plot(n_in_instances, average_time_random, linestyle='dashed', markersize=4, marker='^')
    ax1.plot(n_in_instances, average_time_heuristics, linestyle='dotted', markersize=4, marker='x')
    ax1.set_ylabel("Średni czas działania algorytmu [ms]")
    ax1.set_xlabel("Rozmiar instancji")
    plt.legend(algorithm_names)
    save_plot2(plt, "AverageTime", output_folder)
    plt.close(fig1)

    fig1, ax1 = plt.subplots(figsize=(10, 6))
    plt.yscale("log")
    ax1.plot(n_in_instances, efficiency_greedy, linestyle='solid', markersize=4, marker='o')
    ax1.plot(n_in_instances, efficiency_steepest, linestyle='dashdot', markersize=4, marker='D')
    ax1.plot(n_in_instances, efficiency_random, linestyle='dashed', markersize=4, marker='^')
    ax1.plot(n_in_instances, average_time_heuristics, linestyle='dotted', markersize=4, marker='x')
    ax1.set_ylabel("Efektywność algorytmu - skala logarytmiczna")
    ax1.set_xlabel("Rozmiar instancji")
    plt.legend(algorithm_names)
    save_plot2(plt, "Efficiency", output_folder)
    plt.close(fig1)

    fig, (ax1, ax2) = plt.subplots(2, figsize=(10,9))
    ax1.plot(n_in_instances, average_steps_greedy, linestyle='solid', markersize=4, marker='o')
    ax1.plot(n_in_instances, average_steps_steepest, linestyle='dashdot', markersize=4, marker='D')
    ax1.set_ylabel("Liczba kroków algorytmu")
    ax1.set_xlabel("Rozmiar instancji")
    ax1.legend(algorithm_names[0:2])

    ax2.plot(n_in_instances, average_visit_solutions_greedy, linestyle='solid', markersize=4, marker='o')
    ax2.plot(n_in_instances, average_visit_solutions_steepest, linestyle='dashdot', markersize=4, marker='D')
    ax2.set_ylabel("Liczba ocenionych rozwiązań")
    ax2.set_xlabel("Rozmiar instancji")

    ax2.legend(algorithm_names[0:2])
    save_plot2(plt, "Liczba kroków i ocenionych rozwiazań", output_folder)
    plt.close(fig1)


def task_3():
    output_folder = "Task 3/"
    algorithm_name = ["GreedyLocalSearchAlgorithm", "SteepestLocalSearchAlgorithm"]
    for i in instances:
        data = pd.read_csv('../out/results_task_3_' + i + '.csv', sep=',')
        for algorithm in algorithm_name:
            start_result = data.loc[(data.Algorithm == algorithm) & (data.Type == "StartResult")]
            final_result = data.loc[(data.Algorithm == algorithm) & (data.Type == "FinalResult")]
            fig1, ax1 = plt.subplots(figsize=(10, 6))
            ax1.plot(start_result.iloc[0, 2:], final_result.iloc[0, 2:], 'bo', markersize=5)
            ax1.set_xlabel('Jakość rozwiązania początkowego')
            ax1.set_ylabel('Jakość rozwiązania końcowego')
            save_plot(plt, "T3" + algorithm + i, output_folder, i + "/")
            plt.close(fig1)


def task_4():
    output_folder = "Task 4/"
    algorithm_name = ["GreedyLocalSearchAlgorithm", "SteepestLocalSearchAlgorithm"]
    for i in instances:
        data = pd.read_csv('../out/results_task_4_' + i + '.csv', sep=',')
        for algorithm in algorithm_name:
            fig1, ax1 = plt.subplots(figsize=(10, 6))
            average_distance = data.loc[(data.Algorithm == algorithm) & (data.Type == "AverageDistance")]
            minimum_distance = data.loc[(data.Algorithm == algorithm) & (data.Type == "MinimumDistance")]
            ax1.plot(average_distance.iloc[0, 2:].to_list(), linestyle='-')
            ax1.plot(minimum_distance.iloc[0, 2:].to_list(), linestyle='--')
            ax1.set_xlabel('Liczba restartów')
            ax1.set_ylabel('Dystans')
            plt.xticks(np.arange(0, 301, step=30))
            plt.legend(["Średni dystans", "Minimalny dystans"])
            save_plot(plt, "T4" + algorithm + i, output_folder, i + "/")
            plt.close(fig1)


def task_5():
    output_folder = "Task 5/"
    algorithm_name = ["GreedyLocalSearchAlgorithm", "SteepestLocalSearchAlgorithm", "RandomAlgorithm", "HeuristicsAlgorithm"]
    for i in instances:
        data = pd.read_csv('../out/results_task_5_' + i + '.csv', sep=',')
        fig, ((ax1, ax2), (ax3, ax4)) = plt.subplots(2, 2, figsize=(15, 9))
        all_axes = [ax1, ax2, ax3, ax4]
        ax_id = 0
        for algorithm in algorithm_name:
            quality = data.loc[(data.Algorithm == algorithm) & (data.Type == "Results")]
            similarity = data.loc[(data.Algorithm == algorithm) & (data.Type == "Similarities")]
            ax = all_axes[ax_id]
            ax.plot(quality.iloc[0, 2:], similarity.iloc[0, 2:], 'bo', markersize=4)
            ax.set_xlabel('Jakość rozwiązania')
            ax.set_ylabel('Podobieństwo do optimum globalnego')
            ax_id += 1
            ax.legend([algorithm])
        save_plot(plt, "T5" + i, output_folder, i + "/")
        plt.close(fig)

        for algorithm in algorithm_name:
            quality = data.loc[(data.Algorithm == algorithm) & (data.Type == "Results")]
            similarity = data.loc[(data.Algorithm == algorithm) & (data.Type == "Similarities")]
            fig1, ax1 = plt.subplots(figsize=(10, 6))
            ax1.plot(quality.iloc[0, 2:], similarity.iloc[0, 2:], 'bo', markersize=5)
            ax1.set_xlabel('Jakość rozwiązania')
            ax1.set_ylabel('Podobieństwo do optimum globalnego')
            save_plot(plt, "T5" + algorithm + i, output_folder, i + "/")
            plt.close(fig1)


task_2()
task_3()
task_4()
task_5()
