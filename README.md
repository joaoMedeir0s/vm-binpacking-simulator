# VM Bin Packing Simulator

Simulador em Java que aloca máquinas virtuais (VMs) em servidores "físicos" e mede quantos servidores são necessários, considerando duas dimensões: CPU e memória.

> **Status:** em desenvolvimento. Hoje o projeto tem o modelo, a heurística First Fit, um gerador de carga reproduzível e o cálculo do mínimo teórico. As demais heurísticas, os experimentos em lote e a análise ainda serão adicionados.

## O problema

Em uma nuvem, cada VM pede uma quantidade de CPU e de memória, e precisa ser colocada em algum servidor. Menos servidores ligados significa menos custo e menos energia. Este é o problema de *bin packing* com duas dimensões: dado um conjunto de VMs, em quantos servidores elas cabem?

O objetivo do simulador é **minimizar o número de servidores usados** e comparar o quanto cada estratégia de alocação se aproxima do melhor resultado possível.

## Modelo

- Todos os servidores são idênticos, para simplificar o modelo: **64 CPUs e 256 GB** de memória.
- Cada VM pede uma quantidade de CPU e de memória. Ela só cabe em um servidor se **as duas dimensões** couberem.
- Os servidores são abertos **sob demanda**: se a VM não cabe em nenhum servidor já aberto, abre-se um novo.
- As VMs são sorteadas entre 9 tipos (*flavors*), com pesos, usando **semente fixa**: a mesma semente gera sempre a mesma carga, então os resultados são reproduzíveis.

Os tipos de VM incluem formatos equilibrados (1 CPU : 4 GB, o mesmo formato do servidor), formatos com muita memória e formatos com muita CPU. Eles se complementam entre si e criam fragmentação, que é o que torna a comparação entre heurísticas interessante. Com uma carga só de VMs equilibradas, todas as estratégias chegariam perto do mínimo e a comparação perderia o sentido.

## O que já está implementado

| Componente | O que faz |
|---|---|
| `Vm` | Dados de uma VM (id, CPU, memória). |
| `Server` | Capacidade, uso atual, e `fits`/`place` para checar e colocar VMs. |
| `Heuristic` | Interface que toda estratégia implementa: `choose` (escolhe o servidor) e `order` (ordem de processamento). |
| `FirstFit` | Coloca a VM no primeiro servidor aberto onde ela cabe. |
| `Simulation` | Laço principal: para cada VM, pede um servidor à heurística, abre um novo se necessário e calcula as métricas. |
| `WorkloadGenerator` | Gera a lista de VMs com semente fixa. |
| `Main` | Roda uma simulação e imprime o resultado. |

### Métricas

- **Servidores usados** pela heurística.
- **Mínimo teórico (limite inferior):** soma das demandas dividida pela capacidade, arredondada para cima, para CPU e para memória, ficando com o maior dos dois. Nenhum algoritmo consegue usar menos servidores que isso.
- **Utilização média de CPU e de memória:** fração dos recursos dos servidores abertos que está de fato em uso.
- **Tempo de execução** da alocação, em milissegundos.

## O que vou implementar

- **Mais heurísticas:**
  - **Best Fit:** entre os servidores onde a VM cabe, escolhe o que fica mais cheio depois de recebê-la.
  - **Worst Fit:** entre os servidores onde a VM cabe, escolhe o que fica mais vazio depois de recebê-la.
  - **First Fit Decreasing:** First Fit com as VMs ordenadas da maior para a menor.
- etc.
