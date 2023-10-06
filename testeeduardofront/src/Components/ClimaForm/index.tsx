import React,{useState} from 'react'
import {Container,Input,Button} from './styles'
import axios from "axios"

interface ClimaFormProps{
  onSubmit:(cnpj:string) => void
}


const ClimaForm:React.FC<ClimaFormProps> =({ onSubmit }) => {
  const [cnpj, setCnpj] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
  
    try {
      if (typeof cnpj !== 'string') {
        console.error('CNPJ não é uma string válida');
        return;
      }
      console.log(cnpj)
      const response = await axios.get(`http://localhost:8080/consultar?cnpj=${cnpj}`);
      const responseData = response.data;
      console.log(responseData)
      
      onSubmit(responseData); 
    } catch (error) {
      console.error('Erro ao consultar a API:', error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Container>
      <form onSubmit={handleSubmit}>
          <Input 
             type="text"
             value={cnpj}
              onChange={(event) => setCnpj(event.target.value)}
             placeholder="cnpj" 
          />
        <Button type="submit">Consultar</Button>
      </form>
    </Container>
  )
}
export default ClimaForm;