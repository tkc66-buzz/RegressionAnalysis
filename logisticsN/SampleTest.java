package logisticsN;

import java.util.ArrayList;
import java.util.List;

public class SampleTest {

	public static void main(String[] args) {
		testExp2();
		testExp3();
	}

	static void testExp2() {
		double[][] xip = {
				{ 51, 38, 57, 51, 53, 77, 63, 69, 72, 73 },
				{ 16, 4, 16, 11, 4, 22, 5, 5, 2, 1 }
		};

		double[] yi = {
				3.0, 3.2, 3.3, 3.9, 4.4, 4.5, 4.5, 5.4, 5.4, 6.0
		};
		printResult(xip, yi);
	}

	static void testExp3() {
		double[][] xip = {
				{ 27.22, 29.74, 50.22, 43.00, 37.19, 46.28, 39.50, 51.80, 54.74, 62.61, 53.50 },
				{ 13.0, 14.0, 5.0, 19.0, 6.0, 13.0, 10.0, 2.0, 2.0, 2.0, 8.0 },
				{ 31.0, 30.0, 13.0, 10.0, 9.0, 8.0, 12.0, 15.0, 15.0, 15.0, 8.0 }
		};

		double[] yi = {
				5.6, 6.3, 9.0, 9.2, 9.5, 9.8, 10.0, 11.0, 11.3, 12.5, 13.1
		};
		printResult(xip, yi);
	}

	static void printResult(double[][] xip, double[] yi) {

		// データを準備する
		List<List<Double>> itemsXip = new ArrayList<>();

		for (int i = 0; i < xip.length; i++) {
			itemsXip.add(prepareTestData(xip[i]));
		}
		List<Double> itemsYi = prepareTestData(yi);

		// 計算を行う
		Calculator calc = new Calculator();
		Double beta0 = calc.sectionForExpVarN(itemsXip, itemsYi);
		List<Double> batas = calc.partialRegressionCoefficientsForExpVarN(itemsXip, itemsYi);

		// 結果を表示する
		int n = batas.size();
		StringBuilder msg1 = new StringBuilder();
		StringBuilder msg2 = new StringBuilder();
		String fmt1 = "切片（β0）\t\t：%f\n";
		String fmt2 = "偏回帰係数（β%d）\t：%f\n";
		String fmt3 = "重回帰式（p=%d）\t\t：y = %s\n";
		msg1.append(String.format(fmt1, beta0));
		msg2.append(beta0);

		for (int i = 0; i < n; i++) {
			msg1.append(String.format(fmt2, i + 1, batas.get(i)));
			msg2.append(getMark(batas.get(i)) + batas.get(i) + "x" + i + 1);
		}
		System.out.println(
				msg1.toString() + String.format(fmt3, n, msg2.toString()));
	}

	static List<Double> prepareTestData(double[] sample) {
		List<Double> items = new ArrayList<>();

		for (double data : sample) {
			items.add((double) data);
		}
		return items;
	}

	static String getMark(Double data) {
		String mark = " + ";
		if (data < 0) {
			mark = " ";
		}
		return mark;
	}
}
