import os
import pandas as pd
import matplotlib.pyplot as plt

path_to_charts = "../out/charts/"


def save_plot(plot, name, task):

    if not os.path.exists(path_to_charts):
        os.mkdir(path_to_charts)
    if not os.path.exists(path_to_charts + task):
        os.mkdir(path_to_charts + task)
    plot.savefig(path_to_charts + task + name)


def task_3():
    output_folder = "Task 3/"
    data = pd.read_csv('../out/results_task_3_berlin52.csv', sep=',')
    algorithm_name = ["GreedyLocalSearchAlgorithm", "SteepestLocalSearchAlgorithm"]
    for algorithm in algorithm_name:
        start_result = data.loc[(data.Algorithm == algorithm) & (data.Type == "StartResult")]
        final_result = data.loc[(data.Algorithm == algorithm) & (data.Type == "FinalResult")]
        fig1, ax1 = plt.subplots(figsize=(10, 6))
        ax1.plot(start_result.iloc[0, 2:], final_result.iloc[0, 2:], 'bo')
        save_plot(plt, algorithm, output_folder)


def task_4():
    output_folder = "Task 4/"
    data = pd.read_csv('../out/results_task_4_berlin52.csv', sep=',')
    type_of_result = ["AverageDistance", "MinimumDistance"]
    algorithm_name = ["GreedyLocalSearchAlgorithm", "SteepestLocalSearchAlgorithm"]
    for t in type_of_result:
        for algorithm in algorithm_name:
            result = data.loc[(data.Algorithm == algorithm) & (data.Type == t)]
            fig1, ax1 = plt.subplots(figsize=(10, 6))
            ax1.plot(result.iloc[0, 2:])
            save_plot(plt, t+algorithm, output_folder)


def task_5():
    output_folder = "Task 5/"
    data = pd.read_csv('../out/results_task_5_berlin52.csv', sep=',')
    type_of_result = ["Results", "Similarities"]
    algorithm_name = ["GreedyLocalSearchAlgorithm", "SteepestLocalSearchAlgorithm", "RandomAlgorithm", "HeuristicsAlgorithm"]
    for algorithm in algorithm_name:
        quality = data.loc[(data.Algorithm == algorithm) & (data.Type == "Results")]
        similarity = data.loc[(data.Algorithm == algorithm) & (data.Type == "Similarities")]
        fig1, ax1 = plt.subplots(figsize=(10, 6))
        ax1.plot(quality.iloc[0, 2:], similarity.iloc[0, 2:], 'bo')
        save_plot(plt, algorithm, output_folder)


task_3()
# task_4()
# task_5()
