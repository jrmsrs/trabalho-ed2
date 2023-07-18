package tests;

import estruturas.ArvoreRN;

public class TestArvoreRN {
  public static void main(String[] args) {
    ArvoreRN<Integer> arvore = new ArvoreRN<>();
    arvore.inserir(10);
    arvore.inserir(20);
    arvore.inserir(30);
    arvore.inserir(40);
    arvore.inserir(50);
    arvore.inserir(60);
    arvore.inserir(5);
    arvore.inserir(0);
    arvore.inserir(15);
    arvore.inserir(25);
    arvore.remover(10);
    arvore.remover(15);
    arvore.imprimir();

    arvore.limpar();
    System.out.println();

    arvore.inserir(9);
    arvore.inserir(8);
    arvore.inserir(7);
    arvore.inserir(6);
    arvore.inserir(5);
    arvore.inserir(4);
    arvore.inserir(3);
    arvore.inserir(2);
    arvore.inserir(1);
    arvore.inserir(0);
    arvore.remover(6);
    arvore.imprimir();
  }
}
