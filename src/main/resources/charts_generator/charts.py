import os
import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

path_to_charts = "../out/charts/"
instances = ["a280", "berlin52", "ch130", "ch150", "eil101", "kroA100", "kroC100", "kroD100", "lin105", "pcb442", "pr76", "rd100", "tsp225"]


def save_plot(plot, name, task, instance):

    if not os.path.exists(path_to_charts):
        os.mkdir(path_to_charts)
    if not os.path.exists(path_to_charts + task):
        os.mkdir(path_to_charts + task)
    if not os.path.exists(path_to_charts + task + instance):
        os.mkdir(path_to_charts + task + instance)
    plot.savefig(path_to_charts + task + instance + name)


def task_2():
    output_folder = "Task 2/"

    for i in instances:
        data = pd.read_csv('../out/results_task_2_' + i + '.csv', sep=',')
        algorithm_names = data["Algorithm"].to_list()

        average_results = data["AverageResult"].to_list()
        standard_deviation = data["StandardDeviation"].to_list()
        fig1, ax1 = plt.subplots(figsize=(10, 6))
        plt.yscale("log")
        ax1.set_ylabel("Odległość od optimum - skala logarytmiczna")
        ax1.errorbar(algorithm_names, average_results, standard_deviation, color='b', linestyle='None', fmt='o')
        save_plot(plt, "AverageResult", output_folder, i + "/")
        plt.close(fig1)

        average_results_without_random = data["AverageResult"].to_list()
        average_results_without_random.pop(2)
        standard_deviation_without_random = data["StandardDeviation"].to_list()
        standard_deviation_without_random.pop(2)
        algorithm_names_without_random = data["Algorithm"].to_list()
        algorithm_names_without_random.pop(2)
        fig1, ax1 = plt.subplots(figsize=(10, 6))
        ax1.set_ylabel("Odległość od optimum")
        ax1.errorbar(algorithm_names_without_random, average_results_without_random, standard_deviation_without_random, color='b', linestyle='None', fmt='o')
        save_plot(plt, "AverageResultWithoutRandom", output_folder, i + "/")
        plt.close(fig1)

        best_results = data["BestResult"].to_list()
        fig1, ax1 = plt.subplots(figsize=(10, 6))
        plt.yscale("log")
        ax1.plot(algorithm_names, best_results, 'bo', markersize=5)
        ax1.set_ylabel("Odległość od optimum - skala logarytmiczna")
        save_plot(plt, "BestResult", output_folder, i + "/")
        plt.close(fig1)

        best_results_without_random = data["BestResult"].to_list()
        best_results_without_random.pop(2)
        fig1, ax1 = plt.subplots(figsize=(10, 6))
        ax1.plot(algorithm_names_without_random, best_results_without_random, 'bo', markersize=5)
        ax1.set_ylabel("Odległość od optimum")
        save_plot(plt, "BestResultWithoutRandom", output_folder, i + "/")
        plt.close(fig1)

        average_times = data["AverageTime"].to_list()
        fig1, ax1 = plt.subplots(figsize=(10, 6))
        ax1.plot(algorithm_names, average_times, 'bo', markersize=5)
        ax1.set_ylabel("Średni czas działania algorytmu [s]")
        save_plot(plt, "AverageTime", output_folder, i + "/")
        plt.close(fig1)

        efficiency = data["Efficiency"].to_list()
        fig1, ax1 = plt.subplots(figsize=(10, 6))
        ax1.plot(algorithm_names, efficiency, 'bo', markersize=5)
        ax1.set_ylabel("Efektywność algorytmu")
        save_plot(plt, "Efficiency", output_folder, i + "/")
        plt.close(fig1)

        average_step_number = data["AverageStepNumber"].iloc[0:2].to_list()
        fig1, ax1 = plt.subplots(figsize=(10, 6))
        l1 = ax1.plot(algorithm_names[0:2], average_step_number, 'bo', markersize=4, marker='o')
        ax1.set_ylabel("Liczba kroków algorytmu")

        average_visit_solution_number = data["AverageVisitSolutionNumber"].iloc[0:2].to_list()
        ax2 = ax1.twinx()
        l2 = ax2.plot(algorithm_names[0:2], average_visit_solution_number, 'bo', markersize=7, marker='x')
        ax2.set_ylabel("Liczba ocenionych rozwiązań")
        leg = l1 + l2
        ax1.legend(leg, ["Średnia liczba kroków algorytmu", "Średnia liczba ocenionych rozwiązań"], loc=10)
        ax1.set_ylim([min(average_step_number) - 100, max(average_step_number) + 100])
        save_plot(plt, "Liczba krokow i ocenionych rozwiazan", output_folder, i + "/")
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
            save_plot(plt, algorithm, output_folder, i + "/")
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
            save_plot(plt, algorithm, output_folder, i + "/")
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
        save_plot(plt, i, output_folder, i + "/")
        plt.close(fig)

        for algorithm in algorithm_name:
            quality = data.loc[(data.Algorithm == algorithm) & (data.Type == "Results")]
            similarity = data.loc[(data.Algorithm == algorithm) & (data.Type == "Similarities")]
            fig1, ax1 = plt.subplots(figsize=(10, 6))
            ax1.plot(quality.iloc[0, 2:], similarity.iloc[0, 2:], 'bo', markersize=5)
            ax1.set_xlabel('Jakość rozwiązania')
            ax1.set_ylabel('Podobieństwo do optimum globalnego')
            save_plot(plt, algorithm, output_folder, i + "/")
            plt.close(fig1)


task_2()
# task_3()
# task_4()
# task_5()
