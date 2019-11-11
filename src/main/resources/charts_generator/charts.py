import os
import pandas as pd
import matplotlib.pyplot as plt

path_to_charts = "../out/charts/"

def save_plot(plot, name):

    if not os.path.exists(path_to_charts):
        os.mkdir(path_to_charts)
    plot.savefig(path_to_charts + name)


def task_4():
    data = pd.read_csv('../out/results_task_4_berlin52.csv', sep=',')
    type_of_result = ["AverageDistance", "MinimumDistance"]
    alghoritm_name = ["GreedyLocalSearchAlgorithm", "SteepestLocalSearchAlgorithm"]
    for t in type_of_result:
        for algorithm in alghoritm_name:
            result = data.loc[(data.Algorithm == algorithm) & (data.Type == t)]
            plt.plot(result.iloc[0, 2:])
            save_plot(plt, t+algorithm)


task_4()
