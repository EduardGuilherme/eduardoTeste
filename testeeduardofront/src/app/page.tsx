'use client'
import React, { useState, useEffect } from 'react';
import ClimaForm from '@/Components/ClimaForm';
import ClimaList from '@/Components/ClimaList';

export default function Home() {
  const [climaData, setClimaData] = useState<any | null>(null);

  const handleClimaData = (data: any) => {
    setClimaData(data);
  };

  return (
    <>
      <h1>CONSULTA DE CLIMA</h1>
      <ClimaForm onSubmit={handleClimaData} />
      {climaData && <ClimaList resultado={climaData} />}
    </>
  );
}
