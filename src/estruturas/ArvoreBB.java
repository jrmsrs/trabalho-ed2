package estruturas;

public class ArvoreBB<T extends Comparable<T>> {
  class No {
    T dado;
    No esq;
    No dir;

    public No(T dado) {
      this.dado = dado;
      this.esq = this.dir = null;
    }

    public String imprime(int cor) {
      final String ANSI_RESET = "\u001B[0m";
      String ansiColor = "\u001B[" + (cor % 6 + 31) + "m";
      String str = ansiColor + "{ \"dado\": " + this.dado + ", " + ANSI_RESET;
      str += ansiColor + "\"esq\": " + (this.esq != null ? this.esq.imprime(cor + 1) : null) + ansiColor + ", "
          + ANSI_RESET;
      str += ansiColor + "\"dir\": " + (this.dir != null ? this.dir.imprime(cor + 1) : null) + ansiColor + " }"
          + ANSI_RESET;
      return str;
    }
  }

  protected No raiz;

  public ArvoreBB() {
    this.raiz = null;
  }

  public void inserir(T dado) {
    this.raiz = inserir(this.raiz, dado);
  }

  protected No inserir(No no, T dado) {
    if (no == null) {
      return new No(dado);
    }
    if (dado.compareTo(no.dado) < 0) {
      no.esq = inserir(no.esq, dado);
    } else if (dado.compareTo(no.dado) > 0) {
      no.dir = inserir(no.dir, dado);
    } else {
      return no;
    }
    return no;
  }

  protected No minimo(No no) {
    No atual = no;
    while (atual.esq != null) {
      atual = atual.esq;
    }
    return atual;
  }

  public void remover(T dado) {
    this.raiz = remover(this.raiz, dado);
  }

  // usa tecnica do minimo a direita
  protected No remover(No no, T dado) {
    if (no == null) {
      return no;
    }
    if (dado.compareTo(no.dado) < 0) {
      no.esq = remover(no.esq, dado);
    } else if (dado.compareTo(no.dado) > 0) {
      no.dir = remover(no.dir, dado);
    } else {
      if (no.esq == null) {
        return no.dir;
      } else if (no.dir == null) {
        return no.esq;
      }
      No temp = minimo(no.dir);
      no.dado = temp.dado;
      no.dir = remover(no.dir, temp.dado);
    }
    return no;
  }

  public void remover2(T dado) {
    this.raiz = remover2(this.raiz, dado);
  }

  // usa tecnica do minimo a esquerda
  protected No remover2(No no, T dado) {
    if (no == null) {
      return no;
    }
    if (dado.compareTo(no.dado) < 0) {
      no.esq = remover2(no.esq, dado);
    } else if (dado.compareTo(no.dado) > 0) {
      no.dir = remover2(no.dir, dado);
    } else {
      if (no.dir == null) {
        return no.esq;
      } else if (no.esq == null) {
        return no.dir;
      }
      No temp = minimo(no.esq);
      no.dado = temp.dado;
      no.esq = remover2(no.esq, temp.dado);
    }
    return no;
  }

  public void imprimir() {
    System.out.println(this.raiz.imprime(0));
  }

  public void limpar() {
    this.raiz = null;
  }

  public int getAltura() {
    return getAltura(this.raiz);
  }

  protected int getAltura(No no) {
    if (no == null) {
      return 0;
    }
    int alturaEsq = getAltura(no.esq);
    int alturaDir = getAltura(no.dir);
    return 1 + Math.max(alturaEsq, alturaDir);
  }

  public int getTamanho() {
    return getTamanho(this.raiz);
  }

  protected int getTamanho(No no) {
    if (no == null) {
      return 0;
    }
    return 1 + getTamanho(no.esq) + getTamanho(no.dir);
  }
}
