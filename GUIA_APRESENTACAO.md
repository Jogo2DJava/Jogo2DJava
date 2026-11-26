# Guia Rápido de Apresentação — Os 4 Pilares da POO

> **Dica:** Deixe estas abas abertas na IDE antes de começar:
> 1. `GameObject.java` (pacote `entities`)
> 2. `Bird.java` (pacote `entities`)
> 3. `Pipe.java` (pacote `entities`)
> 4. `ScoreManager.java` (pacote `game`)
> 5. *(Opcional)* `Difficulty.java` e `Game.java` (pacote `game`)
>
> **Para rodar o jogo na hora:** `.\mvnw.cmd compile exec:java` (ou `java -jar target/flappy-bird.jar`)

---

## 1. Apresentando ABSTRAÇÃO (Tempo: ~25 segundos)

* **🖱️ O que abrir:** Aba **`GameObject.java`** (`src/main/java/flappybird/entities/GameObject.java`)
* **👉 Onde apontar:** **Linha 14** (`public abstract class GameObject`) e **Linhas 49 e 56** (`update` e `draw`).

```java
// Linha 14:
public abstract class GameObject {
...
// Linha 49:
public abstract void update();

// Linha 56:
public abstract void draw(Graphics2D g);
```

* **🗣️ O que falar:**
  > *"Professor, vou mostrar a **Abstração** e o **Polimorfismo**.*
  > *A **Abstração** está aqui no pacote `entities`, no arquivo **`GameObject.java`**. Na **linha 14**, criamos a classe abstrata `public abstract class GameObject`, que serve como um molde para tudo que existe no jogo.*
  > *E nas **linhas 49 e 56**, declaramos os métodos abstratos `update()` e `draw()` sem corpo. Isso funciona como um contrato: obriga cada elemento do jogo a programar seu próprio movimento e seu próprio desenho."*

---

## 2. Apresentando POLIMORFISMO (Tempo: ~35 segundos)

* **🖱️ O que abrir:** Aba **`Bird.java`** (linhas 86 e 107) e Aba **`Pipe.java`** (linha 61).
* **👉 Onde apontar:**
  1. Em **`Bird.java`**: aponte para a **linha 86** (`@Override public void update()`) e para a **linha 107** (`@Override public Rectangle getBounds()`).
  2. Em **`Pipe.java`**: aponte para a **linha 61** (`@Override public void update()`).

```java
// 1) Em Bird.java (Linha 86):
@Override
public void update() {
    velocityY += gravity;   // Linha 89 (cai com a gravidade da dificuldade escolhida)
    ...
    moveBy(0, velocityY);   // Linha 97
}

// 2) Em Pipe.java (Linha 61):
@Override
public void update() {
    moveBy(-speed, 0);      // Linha 64 (anda para a esquerda)
}

// 3) Em Bird.java (Linha 107):
@Override
public Rectangle getBounds() {
    int insetX = 3;         // Linha 109 (diminui 3 pixels na colisão)
    int insetY = 3;
    ...
}
```

* **🗣️ O que falar:**
  > *"Já o **Polimorfismo** é fazer objetos diferentes responderem ao mesmo comando, cada um do seu jeito, usando a anotação `@Override`.*
  > *Aqui no arquivo **`Bird.java`**, na **linha 86**, o método `update()` aplica a gravidade para o pássaro cair na vertical.*
  > *Mas se eu clicar em **`Pipe.java`**, na **linha 61**, o mesmo método `update()` faz o cano andar para a esquerda na horizontal.*
  > *Além disso, voltando em **`Bird.java`** na **linha 107**, nós também usamos `@Override` no método `getBounds()` para diminuir a caixa de colisão do pássaro em 3 pixels, deixando o jogo mais justo."*

---

## 3. Apresentando ENCAPSULAMENTO (Tempo: ~30 segundos)

* **🖱️ O que abrir:** Aba **`ScoreManager.java`** (`src/main/java/flappybird/game/ScoreManager.java`) e depois a aba **`Bird.java`**.
* **👉 Onde apontar:**
  1. Em **`ScoreManager.java`**: **linhas 13 e 14** (atributos `private`), **linha 24** (`increment()`) e **linhas 38 e 42** (getters, só leitura).
  2. Em **`Bird.java`**: **linha 27** (`private double velocityY`) e **linha 73** (`flap()`).

```java
// 1) Em ScoreManager.java:
private int score;            // Linha 13
private int bestScore;        // Linha 14

public void increment() {     // Linha 24
    this.score++;             // Linha 25 (sobe só de 1 em 1)
    if (this.score > this.bestScore) {
        this.bestScore = this.score;   // Linha 27 (recorde atualizado automaticamente)
    }
}

public int getScore() { ... }      // Linha 38 (só leitura, sem setScore)
public int getBestScore() { ... }  // Linha 42

// 2) Em Bird.java:
private double velocityY;     // Linha 27

public void flap() {          // Linha 73
    this.velocityY = GameConfig.JUMP_FORCE;   // Linha 74
}
```

* **🗣️ O que falar:**
  > *"O **Encapsulamento** é proteger os dados de um objeto para que ninguém de fora altere eles de qualquer jeito.*
  > *Aqui no **`ScoreManager.java`**, nas **linhas 13 e 14**, os atributos `score` e `bestScore` são `private`. Não existe um `setScore()`: a única forma de mudar a pontuação é pelo método público `increment()` na **linha 24**, que soma de 1 em 1 e já atualiza o recorde sozinho na **linha 27**. Para ler, só existem os getters nas **linhas 38 e 42**.*
  > *A mesma ideia está no **`Bird.java`**: na **linha 27**, a velocidade `velocityY` é `private`. Ninguém consegue jogar o pássaro para cima colocando qualquer número; para voar, é preciso chamar o método `flap()` da **linha 73**, que aplica sempre a mesma força de pulo."*

---

## 4. Apresentando HERANÇA (Tempo: ~30 segundos)

* **🖱️ O que abrir:** Abas **`Bird.java`**, **`Pipe.java`** e **`GameObject.java`**.
* **👉 Onde apontar:**
  1. **`extends GameObject`**: `Bird.java` **linha 25**, `Pipe.java` **linha 15** e `Ground.java` **linha 15**.
  2. **`super(...)`** chamando o construtor da classe mãe: `Bird.java` **linha 50** e `Pipe.java` **linha 53**.
  3. Em **`GameObject.java`**: **linhas 17 a 26** (atributos `x`, `y`, `width`, `height`) e **linha 73** (`protected void moveBy`).

```java
// 1) As classes filhas herdam da classe mãe:
public class Bird extends GameObject {     // Bird.java, Linha 25
public class Pipe extends GameObject {     // Pipe.java, Linha 15
public class Ground extends GameObject {   // Ground.java, Linha 15

// 2) Cada filha repassa posição e tamanho para a mãe:
super(GameConfig.BIRD_START_X, GameConfig.BIRD_START_Y, ...);   // Bird.java, Linha 50
super(x, y, width, height);                                      // Pipe.java, Linha 53

// 3) O que é herdado, em GameObject.java:
private double x;               // Linha 17
private double y;               // Linha 20
private final int width;        // Linha 23
private final int height;       // Linha 26

protected void moveBy(double dx, double dy) { ... }   // Linha 73 (usado por Bird e Pipe)
```

* **🗣️ O que falar:**
  > *"A **Herança** é quando uma classe filha reaproveita tudo o que a classe mãe já tem.*
  > *Aqui no **`Bird.java`**, na **linha 25**, temos `Bird extends GameObject`. O mesmo acontece no **`Pipe.java`** e no **`Ground.java`**, ambos na **linha 15**. Ou seja: o pássaro, o cano e o chão **são um** `GameObject`.*
  > *Na **linha 50 do `Bird`** e na **linha 53 do `Pipe`**, a palavra `super(...)` chama o construtor da classe mãe, passando posição e tamanho.*
  > *E no **`GameObject.java`**, nas **linhas 17 a 26**, estão os atributos `x`, `y`, largura e altura, junto com o método `moveBy()` na **linha 73**. Esse código foi escrito **uma única vez** e as três classes filhas usam sem repetir nada — por exemplo, o `Bird` chama `moveBy(0, velocityY)` e o `Pipe` chama `moveBy(-speed, 0)`."*

---

## 5. Extra: Modos de Dificuldade e Música (Caso sobre tempo — ~20 segundos)

* **🎮 No jogo:** no menu, use **`←` / `→`** (ou **`1` / `2`**, ou clique nos botões) para escolher **MÉDIO** ou **DIFÍCIL**. A tecla **`M`** liga/desliga a música.
* **🖱️ O que abrir:** **`Difficulty.java`** (pacote `game`).
* **👉 Onde apontar:** **Linha 13** (`public enum Difficulty`), **linhas 18 e 29** (`MEDIUM` e `HARD`) e **linhas 37 a 41** (atributos `private final`).
* **🗣️ O que falar:**
  > *"Também adicionamos dois modos de jogo. Cada modo é uma constante do enum `Difficulty` e guarda seus próprios valores de velocidade dos canos, tamanho do vão, intervalo entre canos e gravidade em atributos `private final` (linhas 37 a 41) — isso é **Encapsulamento**, porque ninguém consegue alterar esses valores por fora.*
  > *No `Game.java`, linha 206, o método `setDifficulty()` só deixa trocar o modo quando o jogo está no **menu**; durante a partida a troca é bloqueada. E na linha 66, `applyDifficultySettings()` repassa os valores para o pássaro, os canos e o chão."*

---

## 6. Respostas Curtas para Perguntas do Professor

1. **"Por que `GameObject` é abstrata?"**
   > *"Porque não existe um 'objeto genérico' na tela; só existem objetos reais (pássaro, cano e chão) que seguem o molde do `GameObject`."*
2. **"O que acontece se apagar o `getBounds()` da linha 107 do `Bird`?"**
   > *"Continua funcionando sem erro, porque ele passa a usar o `getBounds()` padrão da linha 63 do `GameObject` (sem os 3 pixels de desconto)."*
3. **"Por que `PipePair` (par de canos) não tem `extends GameObject`?"**
   > *"Porque o par de canos tem um vão vazio no meio para o pássaro passar. Por isso usamos Composição: ele guarda dois objetos `Pipe` separados (`topPipe` e `bottomPipe` nas linhas 18 e 19 de `PipePair.java`)."*
4. **"Por que a dificuldade é um `enum` e não um `int` ou `String`?"**
   > *"Porque o `enum` só aceita os valores que existem (`MEDIUM` e `HARD`), então não dá para passar um valor inválido. E cada modo já carrega seus próprios parâmetros juntos, em vez de espalhar números soltos pelo código."*
5. **"Os testes ainda passam?"**
   > *"Sim, rodando `.\mvnw.cmd test` são **44 testes** passando, incluindo os novos testes da dificuldade e do teclado."*
