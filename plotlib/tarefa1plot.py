"""Plota um gráfico com os dados obtidos em Java: comparativo de alturas de árvores BSTxAVLxRBT"""
import matplotlib.pyplot as plt
import os
import numpy as np
import math

def plot(bst_heights, avl_heights, rbt_heights, N_values, bst_dp=None, avl_dp=None, rbt_dp=None):
    """ Plota o gráfico com matplotlib """
    # Alturas esperadas teoricamente
    bst_expected_heights = [math.log2(N) for N in N_values]

    # Plotar gráfico comparativo
    plt.errorbar(N_values, bst_heights, bst_dp, fmt="o-", label="BST")
    plt.errorbar(N_values, avl_heights, avl_dp, fmt="o-", label="AVL")
    plt.errorbar(N_values, rbt_heights, rbt_dp, fmt="o-", label="RBT")
    plt.plot(N_values, bst_expected_heights, label="Esperado")
    plt.text(500000, 17, "Esperado BST=AVL=RBT=>log(N)")
    plt.xlabel("N (número de elementos)")
    plt.ylabel("Altura da árvore")
    plt.title("Comparação entre o esperado e o obtido empiricamente")
    plt.ticklabel_format(style="plain")
    plt.legend()
    plt.xlim(10000, 1000000)
    plt.savefig("tarefa1.png")

bst_heights = []
avl_heights = []
rbt_heights = []
N_value = []

for i, dir in enumerate(os.listdir("resultados/tarefa1/")):
    with open(f"resultados/tarefa1/teste{i+1}/alturasBB.txt", "r") as file:
        bst_height = [int(height) for height in file.readlines()]
        bst_heights.append(bst_height)
    with open(f"resultados/tarefa1/teste{i+1}/alturasAVL.txt", "r") as file:
        avl_height = [int(height) for height in file.readlines()]
        avl_heights.append(avl_height)
    with open(f"resultados/tarefa1/teste{i+1}/alturasRN.txt", "r") as file:
        rbt_height = [int(height) for height in file.readlines()]
        rbt_heights.append(rbt_height)
    with open(f"resultados/tarefa1/teste{i+1}/amostra.txt", "r") as file:
        N_value = [int(N) for N in file.readlines()]

bst_heights_array = np.array(bst_heights)
avl_heights_array = np.array(avl_heights)
rbt_heights_array = np.array(rbt_heights)

bst_mean = np.mean(bst_heights_array, axis=0)
bst_std = np.std(bst_heights_array, axis=0)
avl_mean = np.mean(avl_heights_array, axis=0)
avl_std = np.std(avl_heights_array, axis=0)
rbt_mean = np.mean(rbt_heights_array, axis=0)
rbt_std = np.std(rbt_heights_array, axis=0)

plot(bst_mean, avl_mean, rbt_mean, N_value, bst_std, avl_std, rbt_std)
