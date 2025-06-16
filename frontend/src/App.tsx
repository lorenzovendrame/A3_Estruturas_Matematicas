import { useState } from "react";

const MIN_SIZE = 2;
const MAX_SIZE = 10;

export default function LinearSystemCalculator() {
    // Inicializa com 2x2 sistema
    const [rows, setRows] = useState(MIN_SIZE);
    const [cols, setCols] = useState(MIN_SIZE);
    // matriz de coeficientes: rows x (cols + 1) -> o +1 é para o termo independente
    // Exemplo: para 2x2, temos 2 equações, 2 variáveis + 1 termo independente
    const [matrix, setMatrix] = useState(
        Array(MIN_SIZE)
            .fill(0)
            .map(() => Array(MIN_SIZE + 1).fill(""))
    );

    // Atualiza o valor de um campo específico
    const handleChange = (rowIndex: number, colIndex: number, value: string) => {
        const newMatrix = matrix.map((row) => row.slice());
        newMatrix[rowIndex][colIndex] = value;
        setMatrix(newMatrix);
    };

    const addRowAndCol = () => {
        if (rows >= MAX_SIZE || cols >= MAX_SIZE) return;

        const newRows = rows + 1;
        const newCols = cols + 1;

        // Atualiza as linhas existentes para adicionar uma nova coluna antes do termo independente
        const updatedMatrix = matrix.map((row) => {
            // Remove o termo independente (última posição)
            const independentTerm = row[row.length - 1];
            // Adiciona uma nova coluna vazia antes do termo independente
            return [...row.slice(0, -1), "", independentTerm];
        });

        // Adiciona uma nova linha com newCols + 1 elementos (cols + 1 variáveis + termo independente)
        const newRow = Array(newCols + 1).fill("");

        // Atualiza o estado
        setRows(newRows);
        setCols(newCols);
        setMatrix([...updatedMatrix, newRow]);
    };

    const removeRowAndCol = () => {
        if (rows <= MIN_SIZE || cols <= MIN_SIZE) return;

        const newRows = rows - 1;
        const newCols = cols - 1;

        // Remove a última linha
        let updatedMatrix = matrix.slice(0, newRows);

        // Remove a penúltima coluna (antes do termo independente) de cada linha
        updatedMatrix = updatedMatrix.map((row) => {
            // Remove a penúltima coluna
            return [...row.slice(0, -2), row[row.length - 1]];
        });

        setRows(newRows);
        setCols(newCols);
        setMatrix(updatedMatrix);
    };

    // Envia os dados para a API
    const handleSubmit = async (e: { preventDefault: () => void; }) => {
        e.preventDefault();

        // Validação: todos os campos devem ser preenchidos com números válidos
        for (let i = 0; i < rows; i++) {
            for (let j = 0; j < cols + 1; j++) {
                if (matrix[i][j] === "") {
                    alert("Por favor, preencha todos os campos.");
                    return;
                }
                if (isNaN(Number(matrix[i][j]))) {
                    alert("Por favor, insira apenas números válidos.");
                    return;
                }
            }
        }

        // Monta o array multidimensional com números convertidos
        // Cada linha: [coeficientes..., termo independente]
        const A = matrix.map((row) => row.slice(0, cols).map(Number)); // coeficientes
        const B = matrix.map((row) => Number(row[cols])); // termos independentes

        const payload = { A, B };

        try {
            const response = await fetch(
                "http://localhost:8080/api/sistemas-lineares/eliminacao-gauss",
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify(payload),
                }
            );

            if (!response.ok) {
                throw new Error(`Erro na requisição: ${response.statusText}`);
            }

            const data = await response.json();
            alert("Resposta da API: " + JSON.stringify(data));
        } catch (error) {
            alert("Erro ao enviar dados: " + (error as Error).message);
        }
    };

    return (
        <div style={{ maxWidth: 900, margin: "20px auto", fontFamily: "Arial" }}>
            <h1>Calculadora de Sistemas Lineares</h1>
            <p>
                Insira os coeficientes e termos independentes do sistema linear.
                Sistema atual: {rows}x{cols}
            </p>

            <div style={{ marginBottom: 10 }}>
                <button onClick={addRowAndCol} disabled={rows >= MAX_SIZE || cols >= MAX_SIZE}>
                    + Equação
                </button>
                <button onClick={removeRowAndCol} disabled={rows <= MIN_SIZE || cols <= MIN_SIZE} style={{ marginLeft: 5 }}>
                    - Equação
                </button>
            </div>

            <form onSubmit={handleSubmit}>
                <table
                    style={{
                        borderCollapse: "collapse",
                        width: "100%",
                        maxWidth: 700,
                        marginBottom: 20,
                    }}
                >
                    <thead>
                    <tr>
                        {[...Array(cols)].map((_, i) => (
                            <th
                                key={"var" + i}
                                style={{ border: "1px solid #ccc", padding: 8, textAlign: "center" }}
                            >
                                x<sub>{i + 1}</sub>
                            </th>
                        ))}
                        <th style={{ border: "1px solid #ccc", padding: 8, textAlign: "center" }}>
                            Termo independente
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    {[...Array(rows)].map((_, rowIndex) => (
                        <tr key={"row" + rowIndex}>
                            {[...Array(cols + 1)].map((_, colIndex) => (
                                <td key={"cell" + rowIndex + "-" + colIndex} style={{ padding: 4 }}>
                                    <input
                                        type="text"
                                        value={matrix[rowIndex][colIndex]}
                                        onChange={(e) => handleChange(rowIndex, colIndex, e.target.value)}
                                        style={{
                                            width: colIndex === cols ? 130 : 60,
                                            padding: 6,
                                            fontSize: 16,
                                            textAlign: "center",
                                        }}
                                        placeholder={colIndex === cols ? "b" : `a${rowIndex + 1}${colIndex + 1}`}
                                    />
                                </td>
                            ))}
                        </tr>
                    ))}
                    </tbody>
                </table>

                <button type="submit" style={{ padding: "10px 20px", fontSize: 16 }}>
                    Enviar para API
                </button>
            </form>
        </div>
    );
}
