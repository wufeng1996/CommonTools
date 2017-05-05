package com.example.algorithm;

import java.util.Random;

/**
 * 排序算法要考虑：
 * 1)时空复杂度
 * 2)稳定性
 * 3)算法简单性
 * 4)待排序记录量大小
 * 5)记录本身信息量大小，移动次数较多的算法不利
 * @author mbc1996
 * @time 2017/5/5
 */
public class SortAlgorithm {
	
	/**
	 * 数组长度
	 * 以下所有算法均假定数组从下标１开始存储数据，实际存储容量为length-1
	 */
	public static final int length = 1024 * 1024 * 20;

	/**
	 * 打印数组数据，当数据量庞大时，会发生极其多的IO操作
	 * @param array
	 */
	public static final void printArray(int[] array) {
//		for (int i = 1; i < array.length; i++) {
//			System.out.print(array[i] + "\t");
//		}
//		System.out.println();
	}

	/**
	 * 插入排序
	 * 
	 */
	/**
	 * 直接插入排序
	 * <p>思路:</p>
	 * 初始化状态，有序区只有一个元素，其他属于无序区.
	 * 把无序区的元素逐个插入到有序区,不满足升序规则，将引起记录移动。数组0号位置作为暂存单元与哨兵
	 * <p>时空复杂度:</p>
	 * 最好请况是正序，只需与有序区最后一个元素比较，不移动O(n)<br>
	 * 最坏情况是逆序，要与有序区所有元素比较，每次比较都引起移动，O(n^2)<br>
	 * 平均情况，O(n^2)<br>
	 * 空间复杂度O(1)
	 * 
	 * @param array 待排序数组
	 * @param n 数组最后一个存储单元
	 * 
	 */
	public static void insertSort(int[] array, int n) {
		int i,j;
		for (i = 2; i <= n; i++) {
			array[0] = array[i];
			for (j = i - 1; array[0] < array[j]; j--) {
				array[j + 1] = array[j];
			}
			array[j + 1] = array[0];
		}

	}

	/**
	 * 希尔排序
	 * <p>思路:</p>
	 * 把一个大序列分割成d个子序列，对每个子序列进行直接插入排序，待整个序列基本有序时，再进行一次完整的直接插入排序
	 * <p>时空复杂度:</p>
	 * 时间:O(nlogn)~O(n^2)<br>
	 * 空间:O(1)
	 * @param array 待排序数组
	 * @param n 数组最后一个存储单元
	 */
	public static void shellSort(int[] array, int n) {
		int i, j, d;
		for (d = n / 2; d >= 1; d = d / 2) {  // 先２个记录为一组，分为d组
			for (i = d + 1; i <= n; i++) {		//　d+1开始为无序区
				array[0] = array[i];
				for (j = i - d; j > 0 && array[0] < array[j]; j = j - d) { //没有哨兵，注意边界
					array[j + d] = array[j];
				}
				array[j + d] = array[0];
			}
		}

	}

	/**
	 * 2.交换类排序
	 * 
	 */
	/**
	 * 冒泡排序算法
	 * <p>思路:</p>
	 * 用exchange变量标识无序区与有序区，初始状态下，整个数组为无序区，一趟排序下来，对最后一次发生交换的位置进行标记，
	 * 标记后面的即为有序区，下一趟排序的排序区间为[1,exchange]
	 * <p>时空复杂度:</p>
	 * 最好请况下正序，两两比较，不发生交换，O(n)<br>
	 * 最坏请况下逆序，两两比较，每次比较引起交换，O(n^2)<br>
	 * 平均O(n^2)<br>
	 * 空间复杂度O(1)
	 * @param array
	 * @param n
	 */
	public static void bubbleSort(int[] array, int n) {
		int exchange = n;
		int i, j, m;
		int bound;
		while (exchange != 0) {
			bound = exchange;
			exchange = 0;
			for (j = 1; j < bound; j++) {
				if (array[j] > array[j + 1]) {
					m = array[j];
					array[j] = array[j + 1];
					array[j + 1] = m;
					exchange = j;
				}
			}
		}

	}

	/**
	 * 
	 * 进行一次划分，每次划分产生一个轴值
	 * @param array　
	 * @param first 数组第一个元素位置
	 * @param end 数组中最后一个元素位置
	 * @return
	 */
	public static int partition(int array[], int first, int end) {
		int i = first, j = end;  //游标i,j分别初始化数组两端
		while (i < j) {  //当i==j，确定轴值位置
			while (i < j && array[i] < array[j]) {
				j--;   //右侧扫描
			}
			if (i < j) {  //不满足则发生交换
				array[0] = array[i];
				array[i] = array[j];
				array[j] = array[0];
				i++;
			}
			while (i < j && array[i] < array[j]) {
				i++;  //左侧扫描
			}
			if (i < j) {
				array[0] = array[i];
				array[i] = array[j];
				array[j] = array[0];
				j--;
			}
		}
		return i;
	}

	/**
	 * 快速排序算法（目前效率最高，使用最广泛的算法）适合记录量大且随机分布的序列
	 * <p>思路:</p>
	 * 每次“划分”产生一个轴值pivot，满足左边的数比它小，右边的数比它大，用递归的思路完成整个排序过程
	 * <p>时空复杂度:</p>
	 * 最好/平均 时间 O(nlogn) 空间 O(logn)<br>
	 * 最坏：逆序或正序 时间O(n^2) 空间O(n)<br>
	 * @param array
	 * @param first
	 * @param end
	 */
	public static void quickSort(int[] array, int first, int end) {
		if (first < end) {
			int pivot = partition(array, first, end);
			quickSort(array, first, pivot - 1);
			quickSort(array, pivot + 1, end);
		}
	}

	/**
	 * ３.选择排序
	 */
	/**
	 * 简单选择排序
	 * <p>思路:</p>
	 * 第i趟排序，从[i,n]的无序区中选出最小值的索引index，完成i与index两个位置处值的交换。
	 * 特点:移动次数少，一趟最多引起一次位置交换，但比较的次数多而且固定
	 * <p>时空复杂度:</p>
	 * 最好，最坏，平均　时间O(n^2) 空间O(1)
	 * @param array
	 * @param n
	 */
	public static void selectSort(int[] array, int n) {
		int i, j, index;
		for (i = 1; i <= n; i++) {
			index = i;
			for (j = i + 1; j <= n; j++) {
				if (array[j] < array[index]) {
					index = j;
				}
			}
			if (index != i) {
				array[0] = array[index];
				array[index] = array[i];
				array[i] = array[0];
			}
		}

	}

	/**
	 * 堆调整算法
	 * @param array
	 * @param n 数组大小
	 * @param k 堆调整的起始位置
	 */
	public static void sift(int[] array, int n, int k) {  
		int i = k, j = 2 * i; //i指向被筛选节点，j指向左孩子
		while (j <= n) {   //筛选还没进行到叶子
			if (j < n && array[j] < array[j + 1]) { //j指向左右孩子中较大值
				j++;
			}
			if (array[i] >= array[j])
				break;
			else {
				array[0] = array[i];
				array[i] = array[j];
				array[j] = array[0];
				i = j;  //交换引起子树堆结构的变化，需要重新调整子树
				j = 2 * i;
			}
		}
	}

	/**
	 * 堆排序
	 * <p>思路:</p>
	 * 堆:一棵基于数组存储的完全二叉树，对于大根堆，满足根节点值比左右子树大。在初始建堆后，
	 * 把根节点与无序区最后一个元素进行交换，而后调整堆
	 * <p>时空复杂度:</p>
	 * 时间:最好最坏平均O(nlogn)<br>
	 * 空间:O(1)
	 * @param array
	 * @param n
	 */
	public static void heapSort(int[] array, int n) {
		int i;
		for (i = n / 2; i >= 1; i--) { //从最后一个分支节点开始筛选，初始建堆
			sift(array, n, i);
		}
		for (i = 1; i < n; i++) { //n个记录，需选择n-1次
			array[0] = array[1]; //根节点array[1]与无序区最后一个元素array[n-i+1]交换
			array[1] = array[n - i + 1];
			array[n - i + 1] = array[0];
			sift(array, n - i, 1);
		}
	}

	
	/**
	 * 一次归并排序算法
	 * 把两个相邻的有序序列合并到另一个空间
	 * @param source 源数组
	 * @param dest 拷贝数组
	 * @param start 序列1的起始位置
	 * @param middle 序列1的终止位置
	 * @param terminal　序列2的终止位置
	 */
	public static void merge(int[] source, int[] dest, int start, int middle, int terminal) {
		int i = start; //i指向序列１的起始位置
		int j = middle + 1; //j指向序列2的起始位置
		int k = start;  //k指向拷贝数组的起始位置
		while (i <= middle && j <= terminal) {
			if (source[i] <= source[j]) {  //取较小者
				dest[k++] = source[i++];
			} else {
				dest[k++] = source[j++];
			}
		}
		if (i <= middle) //对于没处理完的子序列１或２，进行收尾
			while (i <= middle)
				dest[k++] = source[i++];
		else
			while (j <= terminal)
				dest[k++] = source[j++];
	}

	/**
	 * 一趟归并算法
	 * @param source
	 * @param dest
	 * @param n
	 * @param h 子序列长度
	 */
	public static void mergePass(int[] source,int[] dest,int n, int h){
		int i=1;
		while(i<=n-2*h+1){ //表示待归并的两个相邻有序序列的长度均为h，可以执行一次完整的归并
			merge(source, dest, i, i + h - 1, i + 2 * h -1);
			i+=2*h;
		}
		//表示待归并的两个相邻有序序列，一个长度h，一个小于h
		if(i<n - h + 1){
			merge(source, dest, i, i + h -1 , n);
		}else{ //表示只剩下一个有序序列，直接并入结果
			for(int j=i;j<=n;j++)
				dest[j]=source[j];
		}
	}

	/**
	 * 归并排序
	 * <p>思路:</p>
	 * 把整个序列初始化为n个相邻有序序列，每个序列只有１个元素。而后两两归并
	 * <p>时空复杂度:</p>
	 * 时间:O(nlogn)
	 * 空间:O(n) 辅助数组
	 * @param source
	 * @param dest
	 * @param n
	 */
	public static void mergeSort(int[] source, int[] dest,int n){
		int h = 1; //子序列长度
		while(h<=n){ //如果排序趟数为奇数，则最终结果在辅助数组dest中，应传回source
			mergePass(source, dest, n, h);
			h = h * 2;
			mergePass(dest, source, n, h);//若h>n,调用此方法也可以将数组从dest拷贝回source
			h = h * 2;
		}
	}
	
	public static void main(String[] args) {
		long starttime, endtime;
		int[] array = new int[length];
		int[] array1 = new int[length];
		int[] array2 = new int[length];
		int[] array3 = new int[length];
		int[] array4 = new int[length];

		Random random = new Random();
		for (int i = 1; i < array.length; i++) {
			array[i] = random.nextInt(999);
		}

		System.arraycopy(array, 0, array1, 0, array.length);
		System.arraycopy(array, 0, array2, 0, array.length);
		System.arraycopy(array, 0, array3, 0, array.length);
		System.arraycopy(array, 0, array4, 0, array.length);

		System.out.println("数组如下:");
		printArray(array);
		System.out.println("\n开始排序");

		starttime = System.currentTimeMillis();
		mergeSort(array, array4, array.length - 1);
		endtime = System.currentTimeMillis();
		System.out.println("归并排序后数组如下:");
		printArray(array);
		System.out.println("\n耗时:" + (endtime - starttime) + "ms\n");

		starttime = System.currentTimeMillis();
		quickSort(array1, 1, array1.length - 1);
		endtime = System.currentTimeMillis();
		System.out.println("快速排序后数组如下:");
		printArray(array1);
		System.out.println("\n耗时:" + (endtime - starttime) + "ms\n");

		starttime = System.currentTimeMillis();
		shellSort(array2, array2.length - 1);
		endtime = System.currentTimeMillis();
		System.out.println("希尔排序后数组如下:");
		printArray(array2);
		System.out.println("\n耗时:" + (endtime - starttime) + "ms");

		starttime = System.currentTimeMillis();
		heapSort(array3, array3.length - 1);
		endtime = System.currentTimeMillis();
		System.out.println("堆排序后数组如下:");
		printArray(array3);
		System.out.println("\n耗时:" + (endtime - starttime) + "ms");
	}

}
