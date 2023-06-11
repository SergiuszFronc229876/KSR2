package pl.ksr.view;

import lombok.experimental.UtilityClass;
import pl.ksr.logic.calculation.functions.GaussianFunction;
import pl.ksr.logic.calculation.functions.TrapezoidalFunction;
import pl.ksr.logic.calculation.functions.TriangularFunction;
import pl.ksr.logic.calculation.functions.UnionMembershipFunction;
import pl.ksr.logic.calculation.sets.ContinuousSet;
import pl.ksr.logic.calculation.sets.FuzzySet;
import pl.ksr.logic.model.CarDetails;
import pl.ksr.logic.summarization.*;
import pl.ksr.logic.utils.CarDetailsReader;

import java.util.*;

@UtilityClass
public class Data {
    public static List<CarDetails> carDetailsList;
    public static MeasureWeights measureWeights;
    public static List<LinguisticVariable> linguisticVariables;
    public static List<RelativeQuantifier> relativeQuantifiers;
    public static List<Quantifier> predefinedQuantifiers;

    public static void initData() {
        carDetailsList = CarDetailsReader.readDataCsv();

        Map<String, Double> measures = new HashMap<>();
        measures.put("T1", 0.5);
        measures.put("T2", 0.05);
        measures.put("T3", 0.05);
        measures.put("T4", 0.05);
        measures.put("T5", 0.05);
        measures.put("T6", 0.05);
        measures.put("T7", 0.05);
        measures.put("T8", 0.05);
        measures.put("T9", 0.05);
        measures.put("T10", 0.05);
        measures.put("T11", 0.05);
        measureWeights = new MeasureWeights(measures);

        //Cena
        Label najtansze = new Label("najtańsze", "Cena", new FuzzySet(new TrapezoidalFunction(1, 1, 3000, 5000), new ContinuousSet(1, 1_000_000)));
        Label popularne = new Label("popularne", "Cena", new FuzzySet(new TrapezoidalFunction(3000, 5000, 8000, 10000), new ContinuousSet(1, 1_000_000)));
        Label luksusowe = new Label("luksusowe", "Cena", new FuzzySet(new TrapezoidalFunction(8000, 10000, 17000, 25000), new ContinuousSet(1, 1_000_000)));
        Label superLuksusowe = new Label("super-luksusowe", "Cena", new FuzzySet(new TrapezoidalFunction(17000, 25000, 80000, 100000), new ContinuousSet(1, 1_000_000)));
        Label sportowe = new Label("sportowe", "Cena", new FuzzySet(new TrapezoidalFunction(80000, 100000, 200000, 250000), new ContinuousSet(1, 1_000_000)));
        Label superSportowe = new Label("super-sportowe", "Cena", new FuzzySet(new TrapezoidalFunction(200000, 250000, 1_000_000, 1_000_000), new ContinuousSet(1, 1_000_000)));
        List<Label> labelsCena = new ArrayList<>(Arrays.asList(najtansze, popularne, luksusowe, superLuksusowe, sportowe, superSportowe));
        LinguisticVariable cena = new LinguisticVariable("Cena", labelsCena);

        //Przebieg
        Label do_25_000 = new Label("do 25 000 km", "Przebieg", new FuzzySet(new TrapezoidalFunction(0, 0, 10_000, 25_0000), new ContinuousSet(1, 500_000)));
        Label miedzy_10000_75000 = new Label("między 10 000 km a 75 000 km", "Przebieg", new FuzzySet(new TrapezoidalFunction(10_000, 25_000, 50_000, 75_000), new ContinuousSet(1, 500_000)));
        Label miedzy_50000_125000 = new Label("między 50 000 km a 125 000 km", "Przebieg", new FuzzySet(new TrapezoidalFunction(50_000, 75_0000, 100_000, 125_000), new ContinuousSet(1, 500_000)));
        Label miedzy_100000_200000 = new Label("między 100 000 km a 200 000 km", "Przebieg", new FuzzySet(new TrapezoidalFunction(100_000, 125_000, 175_000, 200_000), new ContinuousSet(1, 500_000)));
        Label miedzy_175000_300000 = new Label("między 175 000 km a 300 000 km", "Przebieg", new FuzzySet(new TrapezoidalFunction(175_000, 200_000, 275_000, 300_000), new ContinuousSet(1, 500_000)));
        Label miedzy_275000_375000 = new Label("między 275 000 km a 375 000 km", "Przebieg", new FuzzySet(new TrapezoidalFunction(275_000, 300_000, 350_000, 375_000), new ContinuousSet(1, 500_000)));
        Label ponad_375000 = new Label("ponad 375 000 km", "Przebieg", new FuzzySet(new TrapezoidalFunction(350_000, 375_000, 500_000, 500_000), new ContinuousSet(1, 500_000)));
        List<Label> labelsPrzebieg = new ArrayList<>(Arrays.asList(do_25_000, miedzy_10000_75000, miedzy_50000_125000, miedzy_100000_200000, miedzy_175000_300000, miedzy_275000_375000, ponad_375000));
        LinguisticVariable przebieg = new LinguisticVariable("Przebieg", labelsPrzebieg);

        //Moc silnika
        Label slaba = new Label("słaba", "Moc silnika", new FuzzySet(new TrapezoidalFunction(20, 20, 90, 110), new ContinuousSet(20, 1000)));
        Label umiarkowana = new Label("umiarkowana", "Moc silnika", new FuzzySet(new TriangularFunction(60, 140, 220), new ContinuousSet(20, 1000)));
        Label mocna = new Label("mocna", "Moc silnika", new FuzzySet(new TriangularFunction(140, 220, 300), new ContinuousSet(20, 1000)));
        Label bardzoMocna = new Label("bardzo mocna", "Moc silnika", new FuzzySet(new TrapezoidalFunction(220, 300, 800, 800), new ContinuousSet(20, 1000)));
        List<Label> labelsMoc = new ArrayList<>(Arrays.asList(slaba, umiarkowana, mocna, bardzoMocna));
        LinguisticVariable mocSilnika = new LinguisticVariable("Moc silnika", labelsMoc);

        //Zużycie paliwa
        Label bardzoEkonomiczne = new Label("bardzo ekonomiczne", "Zużycie paliwa na 100 km", new FuzzySet(new TrapezoidalFunction(4.0, 4.0, 4.2, 5.0), new ContinuousSet(4.0, 21.2)));
        Label ekonomiczne = new Label("ekonomiczne", "Zużycie paliwa na 100 km", new FuzzySet(new TrapezoidalFunction(4.2, 5.0, 7.6, 8.4), new ContinuousSet(4.0, 21.2)));
        Label umiarkowanieEkonomiczne = new Label("umiarkowanie ekonomiczne", "Zużycie paliwa na 100 km", new FuzzySet(new TrapezoidalFunction(7.6, 8.4, 10.2, 11.6), new ContinuousSet(4.0, 21.2)));
        Label nieekonomiczne = new Label("nieekonomiczne", "Zużycie paliwa na 100 km", new FuzzySet(new TrapezoidalFunction(10.2, 11.6, 21.2, 21.2), new ContinuousSet(4.0, 21.2)));
        List<Label> labelsZuzycie = new ArrayList<>(Arrays.asList(bardzoEkonomiczne, ekonomiczne, umiarkowanieEkonomiczne, nieekonomiczne));
        LinguisticVariable zuzyciePaliwa = new LinguisticVariable("Zużycie paliwa na 100 km ", labelsZuzycie);

        //Pojemność silnika
        Label do1500 = new Label("do 1500 cm3", "Pojemność silnika", new FuzzySet(new TrapezoidalFunction(700, 700, 1100, 1500), new ContinuousSet(700, 9100)));
        Label miedzy1300_2100 = new Label("miedzy 1300 cm3 a 2100 cm3", "Pojemność silnika", new FuzzySet(new TrapezoidalFunction(1300, 1600, 1800, 2100), new ContinuousSet(700, 9100)));
        Label miedzy1800_2700 = new Label("miedzy 1800 cm3 a 2700 cm3", "Pojemność silnika", new FuzzySet(new TrapezoidalFunction(1800, 2100, 2400, 2700), new ContinuousSet(700, 9100)));
        Label miedzy2400_3500 = new Label("miedzy 2400 cm3 a 3500 cm3", "Pojemność silnika", new FuzzySet(new TrapezoidalFunction(2400, 2700, 3200, 3500), new ContinuousSet(700, 9100)));
        Label miedzy3200_4700 = new Label("miedzy 3200 cm3 a 4700 cm3", "Pojemność silnika", new FuzzySet(new TrapezoidalFunction(3200, 3500, 4400, 4700), new ContinuousSet(700, 9100)));
        Label miedzy4400_6200 = new Label("miedzy 4400 cm3 a 6200 cm3", "Pojemność silnika", new FuzzySet(new TrapezoidalFunction(4400, 4700, 5900, 6200), new ContinuousSet(700, 9100)));
        Label powyzej5900 = new Label("powyżej 5900 cm3", "Pojemność silnika", new FuzzySet(new TrapezoidalFunction(5900, 6200, 9100, 9100), new ContinuousSet(700, 9100)));
        List<Label> labelsPojemnosc = new ArrayList<>(Arrays.asList(do1500, miedzy1300_2100, miedzy1800_2700, miedzy2400_3500, miedzy3200_4700, miedzy4400_6200, powyzej5900));
        LinguisticVariable pojemnoscSilnika = new LinguisticVariable("Pojemność silnika", labelsPojemnosc);

        //Długość
        Label mikrosamochod = new Label("mikrosamochód", "Długość", new FuzzySet(new TrapezoidalFunction(2.4, 2.4, 3.1, 3.5), new ContinuousSet(2.4, 6.6)));
        Label miejski = new Label("samochód miejski", "Długość", new FuzzySet(new TrapezoidalFunction(3.1, 3.5, 4.0, 4.2), new ContinuousSet(2.4, 6.6)));
        Label kompaktowy = new Label("samochód kompaktowy", "Długość", new FuzzySet(new TrapezoidalFunction(4.0, 4.2, 4.5, 4.7), new ContinuousSet(2.4, 6.6)));
        Label klasySredniej = new Label("samochód klasy średniej", "Długość", new FuzzySet(new TrapezoidalFunction(4.5, 4.7, 4.9, 5.1), new ContinuousSet(2.4, 6.6)));
        Label klasySredniejWyzszej = new Label("samochód klasy średniej wyższej", "Długość", new FuzzySet(new TrapezoidalFunction(4.9, 5.1, 5.3, 5.5), new ContinuousSet(2.4, 6.6)));
        Label wyzszej = new Label("samochód klasy wyższej", "Długość", new FuzzySet(new TrapezoidalFunction(5.3, 5.5, 6.6, 6.6), new ContinuousSet(2.4, 6.6)));
        List<Label> labelsDlugosc = new ArrayList<>(Arrays.asList(mikrosamochod, miejski, kompaktowy, klasySredniej, klasySredniejWyzszej, wyzszej));
        LinguisticVariable dlugosc = new LinguisticVariable("Długość", labelsDlugosc);

        //Zbiornik Paliwa
        Label mala = new Label("pozwalająca przejechać około 500 km bez tankowania Volkswagen Golfem 5 z silnikiem o pojemności 1400 cm3", "Pojemność zbiornika paliwa", new FuzzySet(new UnionMembershipFunction(new TrapezoidalFunction(20, 20, 35, 35), new GaussianFunction(35, 4, 35, 77)), new ContinuousSet(20, 160)));
        Label standardowa = new Label("pozwalająca przejechać około 1000 km bez tankowania Volkswagen Golfem 5 z silnikiem o pojemności 1400 cm3", "Pojemność zbiornika paliwa", new FuzzySet(new GaussianFunction(65, 4, 28, 111), new ContinuousSet(20, 160)));
        Label spora = new Label("pozwalająca przejechać około 1500 km bez tankowania Volkswagen Golfem 5 z silnikiem o pojemności 1400 cm3", "Pojemność zbiornika paliwa", new FuzzySet(new GaussianFunction(108, 4, 53, 108), new ContinuousSet(20, 160)));
        List<Label> labelsZbiornik = new ArrayList<>(Arrays.asList(mala, standardowa, spora));
        LinguisticVariable pojemnoscZbiornikaPaliwa = new LinguisticVariable("Pojemność zbiornika paliwa", labelsZbiornik);

        //Rozstaw osi
        Label krotki = new Label("krótki", "Rozstaw osi", new FuzzySet(new TrapezoidalFunction(1.6, 1.6, 1.8, 2.4), new ContinuousSet(1.6, 4.2)));
        Label sredni = new Label("średni", "Rozstaw osi", new FuzzySet(new TrapezoidalFunction(1.8, 2.4, 2.8, 3.2), new ContinuousSet(1.6, 4.2)));
        Label dlugi = new Label("długi", "Rozstaw osi", new FuzzySet(new TrapezoidalFunction(2.8, 3.2, 4.2, 4.2), new ContinuousSet(1.6, 4.2)));
        List<Label> labelsRozstawOsi = new ArrayList<>(Arrays.asList(krotki, sredni, dlugi));
        LinguisticVariable rozstawOsi = new LinguisticVariable("Rozstaw osi", labelsRozstawOsi);

        //Moment obrotowy
        Label niski = new Label("niski", "Moment obrotowy", new FuzzySet(new TrapezoidalFunction(100, 100, 180, 230), new ContinuousSet(100, 1100)));
        Label normalny = new Label("normalny", "Moment obrotowy", new FuzzySet(new TriangularFunction(150, 255, 380), new ContinuousSet(100, 1100)));
        Label wysoki = new Label("wysoki", "Moment obrotowy", new FuzzySet(new TriangularFunction(260, 365, 470), new ContinuousSet(100, 1100)));
        Label chipTuning = new Label("chip tuning", "Moment obrotowy", new FuzzySet(new TrapezoidalFunction(430, 510, 1100, 1100), new ContinuousSet(100, 1100)));
        List<Label> labelsMomentObrotowy = new ArrayList<>(Arrays.asList(niski, normalny, wysoki, chipTuning));
        LinguisticVariable momentObrotowy = new LinguisticVariable("Moment obrotowy", labelsMomentObrotowy);

        //Szerokosc
        Label waski = new Label("węższy od Volkswagena Passata B5", "Szerokość", new FuzzySet(new TrapezoidalFunction(1.50, 1.50, 1.60, 1.70), new ContinuousSet(1.50, 2.60)));
        Label zwyczajny = new Label("szerokość Volkswagena Passata B5", "Szerokość", new FuzzySet(new TrapezoidalFunction(1.60, 1.70, 1.80, 2.00), new ContinuousSet(1.50, 2.60)));
        Label szeroki = new Label("szerszy od Volkswagena Passata B5 o 20 cm", "Szerokość", new FuzzySet(new TrapezoidalFunction(1.80, 2.00, 2.20, 2.30), new ContinuousSet(1.50, 2.60)));
        Label bardzoSzeroki = new Label("szerszy od Volkswagena Passata B5 o 60 cm", "Szerokość", new FuzzySet(new TrapezoidalFunction(2.20, 2.30, 2.60, 2.60), new ContinuousSet(1.50, 2.60)));
        List<Label> labelsSzerokosc = new ArrayList<>(Arrays.asList(waski, zwyczajny, szeroki, bardzoSzeroki));
        LinguisticVariable szerokosc = new LinguisticVariable("Szerokość", labelsSzerokosc);

        //Zmienne lingwistyczne
        linguisticVariables = new ArrayList<>();
        linguisticVariables.addAll(new ArrayList<>(Arrays.asList(cena, przebieg, mocSilnika, zuzyciePaliwa, pojemnoscSilnika, dlugosc, pojemnoscZbiornikaPaliwa, rozstawOsi, momentObrotowy, szerokosc)));

        //Kwantyfikatory Względne
        RelativeQuantifier prawieZaden = new RelativeQuantifier("Prawie żaden", new FuzzySet(new TrapezoidalFunction(0.00, 0.00, 0.10, 0.20), new ContinuousSet(0, 1)));
        RelativeQuantifier niewiele = new RelativeQuantifier("Niewiele", new FuzzySet(new TrapezoidalFunction(0.10, 0.20, 0.25, 0.30), new ContinuousSet(0, 1)));
        RelativeQuantifier mniejNizPolowa = new RelativeQuantifier("Mniej niż połowa", new FuzzySet(new TrapezoidalFunction(0.25, 0.30, 0.40, 0.45), new ContinuousSet(0, 1)));
        RelativeQuantifier okoloPolowa = new RelativeQuantifier("Około połowa", new FuzzySet(new TrapezoidalFunction(0.40, 0.45, 0.55, 0.60), new ContinuousSet(0, 1)));
        RelativeQuantifier sporaCzesc = new RelativeQuantifier("Spora część", new FuzzySet(new TrapezoidalFunction(0.55, 0.60, 0.65, 0.75), new ContinuousSet(0, 1)));
        RelativeQuantifier niemalWszystkie = new RelativeQuantifier("Niemal wszystkie", new FuzzySet(new TrapezoidalFunction(0.65, 0.75, 1.00, 1.00), new ContinuousSet(0, 1)));
        relativeQuantifiers = new ArrayList<>();
        relativeQuantifiers.addAll(new ArrayList<>(Arrays.asList(prawieZaden, niewiele, mniejNizPolowa, okoloPolowa, sporaCzesc, niemalWszystkie)));

        //Kwantyfikatory Absolutne
        AbsoluteQuantifier mniejNiz2000 = new AbsoluteQuantifier("Mniej niż 2000", new FuzzySet(new TrapezoidalFunction(0, 0, 1000, 2000), new ContinuousSet(0, 10_000)));
        AbsoluteQuantifier okolo3000 = new AbsoluteQuantifier("Około 3000", new FuzzySet(new TrapezoidalFunction(1000, 2000, 3000, 4000), new ContinuousSet(0, 10_000)));
        AbsoluteQuantifier miedzy3000_7000 = new AbsoluteQuantifier("Między 3000 a 7000", new FuzzySet(new TrapezoidalFunction(3000, 4000, 6000, 7000), new ContinuousSet(0, 10_000)));
        AbsoluteQuantifier blisko7000 = new AbsoluteQuantifier("Blisko 7000", new FuzzySet(new TrapezoidalFunction(6000, 7000, 7500, 8500), new ContinuousSet(0, 10_000)));
        AbsoluteQuantifier ponad8000 = new AbsoluteQuantifier("Ponad 8000", new FuzzySet(new TrapezoidalFunction(7500, 8000, 10000, 10000), new ContinuousSet(0, 10_000)));
        List<AbsoluteQuantifier> absoluteQuantifiers = new ArrayList<>();
        absoluteQuantifiers.addAll(new ArrayList<>(Arrays.asList(mniejNiz2000, okolo3000, miedzy3000_7000, blisko7000, ponad8000)));

        predefinedQuantifiers = new ArrayList<>();
        predefinedQuantifiers.addAll(absoluteQuantifiers);
        predefinedQuantifiers.addAll(relativeQuantifiers);
    }
}
