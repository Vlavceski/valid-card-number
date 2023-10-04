import React, { useState } from 'react';
import './App.css';

const App = () => {
  const [cardData, setCardData] = useState({
  cardNumber: '',
  expiryDate: '',
  cvv: '',
});
const [validationResult, setValidationResult] = useState(null);

const handleChange = (event) => {
  const { name, value } = event.target;
  setCardData({ ...cardData, [name]: value });
};

const handleSubmit = async (event) => {
  event.preventDefault();

  try {
    const response = await fetch('http://localhost:8080/api/validate', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(cardData),
      
    });

    if (response.ok) {
      const result = await response.json();
      setValidationResult(result.success ? 'Success' : 'Failure');
    } else {
      setValidationResult('Response - Failure');
    }
  } catch (error) {
    console.error('Error:', error);
    setValidationResult('Failure - Error');
  }
};
  return (
    <div className="App">
    <h1>Credit Card Validation</h1>
    <form onSubmit={handleSubmit}>
      <div className="form-group">
        <label htmlFor="cardNumber">Card Number:</label>
        <input
          type="text"
          id="cardNumber"
          name="cardNumber"
          value={cardData.cardNumber}
          onChange={handleChange}
          required
        />
      </div>
      <div className="form-group">
        <label htmlFor="expiryDate">Expiry Date (MM/YY):</label>
        <input
          type="text"
          id="expiryDate"
          name="expiryDate"
          value={cardData.expiryDate}
          onChange={handleChange}
          placeholder="MM/YY"
          required
        />
      </div>
      <div className="form-group">
        <label htmlFor="cvv">CVV:</label>
        <input
          type="text"
          id="cvv"
          name="cvv"
          value={cardData.cvv}
          onChange={handleChange}
          required
        />
      </div>
      <button type="submit">Validate</button>
    </form>
    {validationResult && (
      <div className={`validation-result ${validationResult.toLowerCase()}`}>
        {validationResult}
      </div>
    )}
  </div>
  )
}

export default App
