const boxImgA = document.getElementById('box-a-img');
const boxTempA = document.getElementById('box-a-temp');

async function fetchWeatherJSON() {
    const response = await fetch("https://api.openweathermap.org/data/2.5/weather?q=Plovdiv&appid=e80b8bd999a5a20029a930973b3e8a4b");
    const weather = await response.json();
    return weather;
}

 fetchWeatherJSON()
    .then(info => {
        //Formula Kelvin to Celsius 299K − 273.15 = 25.85°C
        boxTempA.innerText = Math.round(info.main.temp - 273.15);
        boxImgA.src = '/images/weather-icons/' + info.weather[0].icon + '.png';

    })