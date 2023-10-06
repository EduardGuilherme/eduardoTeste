import React from 'react'
import {ContainerList,ItemList} from './styles'

interface ClimaListProps {
    resultado: {
      temperatura_atual: string;
      Condicao_tempo: string;
      temperatura_min_max: {
        temperaturaMinima: string;
        temperaturaMaxima: string;
      };
    };
  }

function ClimaList({resultado}:ClimaListProps) {
    if (!resultado) {       
        return null; 
    }  
return (
    <ContainerList>
        <ItemList>
        <div>     
            <p>Temperatura Atual: {resultado.temperatura_atual}</p>
            <p>Condição do Tempo: {resultado.Condicao_tempo}</p>
            <p> Temperatura Minima e Máxima Prevista para o dia:
                Minimo: {resultado.temperatura_min_max.temperaturaMinima} 
                {' '} Máximo: {resultado.temperatura_min_max.temperaturaMaxima}</p>
        </div>
        </ItemList>
    </ContainerList>
  )
}
export default ClimaList;