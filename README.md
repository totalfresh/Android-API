# Currency Conversion App

**Polska wersja znajduje się poniżej wersji angielskiej.**

This Android application allows users to convert between different currencies using real-time exchange rates. It also provides functionality to swap the base and target currencies, as well as to view historical exchange rates.

## Features

- **Real-time Currency Conversion**: Enter an amount and select the base and target currencies to get the converted amount.
- **Swap Currencies**: Easily swap the base and target currencies with a single button click.
- **View Historical Exchange Rates**: See the exchange rate trends for the past 30 days (Note: historical data is not available due to API limitations).

## How to Use

### Main Screen

1. **Select Currencies**: Choose the base currency and the target currency from the dropdown spinners.
2. **Enter Amount**: Input the amount you want to convert in the text field.
3. **Swap Currencies**: Click the swap button (double arrow icon) to switch the base and target currencies.
4. **View Result**: The converted amount will be displayed along with the current exchange rate.

### History Screen

1. **View Historical Rates**: Click the "History" button to view the historical exchange rates for the selected currencies.
2. **Note**: Historical data may not be available due to API limitations. The feature requires a premium API subscription, which was available during the development phase.

## API Usage

- The app uses the ExchangeRate-API to fetch real-time exchange rates.
- **API Key**: The API key is hardcoded in the app for demonstration purposes. Replace it with your own key for production use.
- **API Limitations**: The free version of the API does not support historical data, which requires a premium subscription.

## Limitations

- **Historical Data**: The historical data feature will not work without a premium API subscription. The API key provided during development had temporary access to premium features.
- **Network Connectivity**: The app requires an active internet connection to fetch exchange rates.

## Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/totalfresh/Android-API.git
    ```

2. Open the project in Android Studio.

3. Build and run the app on an Android device or emulator.

---

# Aplikacja do Konwersji Walut

Ta aplikacja na Androida umożliwia użytkownikom konwersję między różnymi walutami przy użyciu aktualnych kursów wymiany. Posiada również funkcję zamiany walut miejscami oraz przeglądania historycznych kursów wymiany.

## Funkcje

- **Kursy Walut w Czasie Rzeczywistym**: Wprowadź kwotę i wybierz waluty bazową oraz docelową, aby uzyskać przeliczoną kwotę.
- **Zamiana Walut**: Łatwo zamień waluty bazową i docelową za pomocą jednego przycisku.
- **Przegląd Historycznych Kursów Wymiany**: Zobacz trendy kursów wymiany z ostatnich 30 dni (Uwaga: dane historyczne nie są dostępne z powodu ograniczeń API).

## Jak Korzystać

### Główny Ekran

1. **Wybierz Waluty**: Wybierz walutę bazową i docelową z rozwijanych list.
2. **Wprowadź Kwotę**: Wprowadź kwotę do przeliczenia w polu tekstowym.
3. **Zamień Waluty**: Kliknij przycisk zamiany (ikona z podwójną strzałką), aby zamienić waluty miejscami.
4. **Zobacz Wynik**: Przeliczona kwota zostanie wyświetlona wraz z aktualnym kursem wymiany.

### Ekran Historii

1. **Zobacz Historyczne Kursy**: Kliknij przycisk "Historia", aby zobaczyć historyczne kursy wymiany dla wybranych walut.
2. **Uwaga**: Dane historyczne mogą być niedostępne z powodu ograniczeń API. Funkcja ta wymaga subskrypcji premium API, która była dostępna podczas fazy rozwoju.

## Wykorzystanie API

- Aplikacja korzysta z ExchangeRate-API do pobierania aktualnych kursów wymiany.
- **Klucz API**: Klucz API jest zakodowany w aplikacji do celów demonstracyjnych. Wymień go na własny klucz do użycia w produkcji.
- **Ograniczenia API**: Bezpłatna wersja API nie obsługuje danych historycznych, co wymaga subskrypcji premium.

## Ograniczenia

- **Dane Historyczne**: Funkcja danych historycznych nie będzie działać bez subskrypcji premium API. Klucz API udostępniony podczas rozwoju miał tymczasowy dostęp do funkcji premium.
- **Łączność z Siecią**: Aplikacja wymaga aktywnego połączenia internetowego do pobierania kursów wymiany.

## Instalacja

1. Sklonuj repozytorium:
    ```bash
    git clone https://github.com/totalfresh/Android-API.git
    ```

2. Otwórz projekt w Android Studio.

3. Zbuduj i uruchom aplikację na urządzeniu z Androidem lub emulatorze.

Projekt został stworzony przez [Twoje Imię]. Jeśli masz pytania lub uwagi, skontaktuj się ze mną poprzez [Twój e-mail].
