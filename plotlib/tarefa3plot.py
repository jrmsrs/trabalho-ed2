"""Plota um gráfico com os dados obtidos em Java: comparativo de alturas de árvores BSTxAVLxRBT"""
import matplotlib.pyplot as plt
import os
import numpy as np

def plot(bst_heights, avl_heights, N_values, bst_dp=None, avl_dp=None):
    """ Plota o gráfico com matplotlib """
    # Alturas esperadas teoricamente
    bst_expected_heights = [16 for N in N_values]
    avl_expected_heights = [10 for N in N_values]

    # Plotar gráfico comparativo
    plt.errorbar(N_values, bst_heights, bst_dp, fmt="o-", label="BST")
    plt.errorbar(N_values, avl_heights, avl_dp, fmt="o-", label="AVL")
    plt.plot(N_values, bst_expected_heights, label="Esperado BST")
    plt.plot(N_values, avl_expected_heights, label="Esperado AVL")
    plt.text(1000, 60, "Esperado: se manter na altura original")
    plt.xlabel("N (número de elementos)")
    plt.ylabel("Altura da árvore")
    plt.title("Comparação entre o esperado e o obtido empiricamente")
    plt.ticklabel_format(style="plain")
    plt.legend()
    plt.xlim(100, 10000)
    # plt.show()
    plt.savefig("tarefa3.png")

bst_heights = []
avl_heights = []
N_value = []

for i, dir in enumerate(os.listdir("resultados/tarefa3/")):
    with open(f"resultados/tarefa3/teste{i+1}/alturasBB.txt", "r") as file:
        # bst_height = [ 16, 16, 16, 17 ]
        bst_height = [int(height) for height in file.readlines()]
        # bst_heights = [ [16, 16, 16, 17], ... ]
        bst_heights.append(bst_height)
    with open(f"resultados/tarefa3/teste{i+1}/alturasAVL.txt", "r") as file:
        avl_height = [int(height) for height in file.readlines()]
        avl_heights.append(avl_height)
    with open(f"resultados/tarefa3/teste{i+1}/amostra.txt", "r") as file:
        N_value = [int(N) for N in file.readlines()]

bst_heights_array = np.array(bst_heights)
avl_heights_array = np.array(avl_heights)

bst_mean = np.mean(bst_heights_array, axis=0)
bst_std = np.std(bst_heights_array, axis=0)
avl_mean = np.mean(avl_heights_array, axis=0)
avl_std = np.std(avl_heights_array, axis=0)

plot(bst_mean, avl_mean, N_value, bst_std, avl_std)
