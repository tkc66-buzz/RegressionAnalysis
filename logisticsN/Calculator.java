package logisticsN;

import java.util.ArrayList;
import java.util.List;

public class Calculator {
	/**
	 * 説明変数がn個の場合の切片を計算する
	 * @param itemXin 項目リスト（Xip）
	 * @param itemYi 項目リスト（Yi）
	 * @return 結果
	 */
	public Double sectionForExpVarN(
			List<List<Double>> itemsXip, final List<Double> itemsYi) {
		List<Double> xbars = new ArrayList<>();

		for (List<Double> item : itemsXip) {
			xbars.add(average(item));
		}
		Double ybar = average(itemsYi);
		List<Double> batas = partialRegressionCoefficientsForExpVarN(itemsXip, itemsYi);

		double result = ybar;

		for (int i = 0; i < batas.size(); i++) {
			result -= (batas.get(i) * xbars.get(i));
		}
		return result;
	}

	/**
	 * 説明変数がn個の場合の偏回帰係数を計算する
	 * @param itemsXip 項目リスト（Xip）
	 * @param itemsYi 項目リスト（Yi）
	 * @return 結果
	 */
	public List<Double> partialRegressionCoefficientsForExpVarN(
			List<List<Double>> itemsXip, final List<Double> itemsYi) {
		List<List<Double>> coefficientMatrix = new ArrayList<>();
		int n = itemsXip.size();

		for (int i = 0; i < n; i++) {
			List<Double> row = new ArrayList<>();

			for (int j = 0; j < n; j++) {

				if (i == j) {
					row.add(sumOfSquares(itemsXip.get(i)));

				} else {
					row.add(deviationSumOfProduct(itemsXip.get(i), itemsXip.get(j)));
				}
			}
			row.add(deviationSumOfProduct(itemsXip.get(i), itemsYi));
			coefficientMatrix.add(row);
		}
		return systemOfEquations(coefficientMatrix);
	}

	/**
	 * 連立方程式を計算する
	 * @param coefficientMatrix m行n列の係数行列
	 * @return 結果
	 */
	public List<Double> systemOfEquations(List<List<Double>> coefficientMatrix) {
		int n = coefficientMatrix.size();
		int m = coefficientMatrix.get(0).size();
		double[][] matrix = new double[n][];

		for (int i = 0; i < n; i++) {
			double[] row = new double[m];

			for (int j = 0; j < m; j++) {
				row[j] = coefficientMatrix.get(i).get(j);
			}
			matrix[i] = row;
		}
		return systemOfEquations(matrix);
	}

	/**
	 * 連立方程式を計算する
	 * @param coefficientMatrix m行n列の係数行列
	 * @return 結果
	 */
	public List<Double> systemOfEquations(final double[][] coefficientMatrix) {
		List<Double> result = new ArrayList<>();
		double[][] matrix = coefficientMatrix;
		int n = matrix.length;
		int m = matrix[0].length;

		for (int k = 0; k < n; k++) {
			double p = matrix[k][k];

			for (int j = k; j < m; j++) {
				matrix[k][j] = matrix[k][j] / p;
			}

			for (int i = 0; i < n; i++) {

				if (i != k) {
					double d = matrix[i][k];

					for (int j = k; j < m; j++) {
						matrix[i][j] = matrix[i][j] - d * matrix[k][j];
					}
				}
			}
		}

		for (int i = 0; i < n; i++) {
			result.add(matrix[i][n]);
		}

		return result;
	}

	/**
	 * 偏差積和を計算する
	 * @param itemXi 項目リスト（Xi）
	 * @param itemYi 項目リスト（Yi）
	 * @return 結果
	 */
	public Double deviationSumOfProduct(final List<Double> itemsXi, final List<Double> itemsYi) {
		List<Double> itemsXiYi = new ArrayList<>();
		int n = itemsXi.size();

		for (int i = 0; i < n; i++) {
			itemsXiYi.add(itemsXi.get(i) * itemsYi.get(i));
		}
		Double xiyiSum = sum(itemsXiYi);
		Double xiSum = sum(itemsXi);
		Double yiSum = sum(itemsYi);
		return xiyiSum - ((xiSum * yiSum) / n);
	}

	/**
	 * 平方和を計算する
	 * @param items 項目リスト
	 * @return 結果
	 */
	public Double sumOfSquares(final List<Double> items) {
		Double xbar = average(items);
		List<Double> squares = new ArrayList<>();

		for (Double item : items) {
			Double sqare = (item - xbar) * (item - xbar);
			squares.add(sqare);
		}
		return sum(squares);
	}

	/**
	 * 平均値を計算する
	 * @param items 項目リスト
	 * @return 結果
	 */
	public Double average(final List<Double> items) {
		return sum(items) / items.size();
	}

	/**
	 * 総和を計算する
	 * @param items 項目リスト
	 * @return 結果
	 */
	public Double sum(final List<Double> items) {
		Double result = 0.0;

		for (Double item : items) {
			result += item;
		}
		return result;
	}
}
