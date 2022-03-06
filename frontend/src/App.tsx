import './App.css';
import React from 'react';
import {Outlet} from "react-router-dom";
import {useTranslation} from "react-i18next";

function App() {
    const { t } = useTranslation();
    
    return (
    <div>
        <div><h1 className='color-light' id='text'>{t('title')}</h1></div>
        <div><Outlet /></div>
    </div>
);
}

export default App;
