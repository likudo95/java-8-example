package com.company;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class Main {

	static Function<Integer, Integer> randomFunction = max -> new Random().nextInt(max + 1);
	static BiFunction<Integer, Integer, Integer> randomBiFunction = (min, max) -> new Random().nextInt((max + 1) - min);

	static Supplier<Integer> randomSupplier = () -> new Random().nextInt(10);

	static Consumer<Dev> devDetail = dev -> System.out
			.println("Name:" + dev.getName() + " Skill: " + dev.getSkill() + " exp: " + dev.getExp());
	static Consumer<Dev2> devDetail2 = dev -> System.out.println("Name:" + dev.getName() + " Skill: "
			+ dev.getSkillList().get(0).getSkill() + " exp: " + dev.getSkillList().get(0).getExp() + " Skill: "
			+ dev.getSkillList().get(1).getSkill() + " exp: " + dev.getSkillList().get(1).getExp());

	static Predicate<Skill> skillJavaPredict = skill -> {
		return "JAVA".equals(skill.getSkill());
	};

	static Predicate<Skill> skillJava3Year = skill -> "JAVA".equals(skill.getSkill()) && skill.getExp() >= 3;

	static ToIntFunction<Dev2> expJavaDev = dev -> {
		return dev.getSkillList().stream().filter(skillJavaPredict).findFirst().get().getExp();
	};

	static List<Dev> data() {
		List<Dev> data = new ArrayList<Dev>();
		for (int i = 1; i <= 9; i++) {
			Dev dev = new Dev(i, "Dev" + i, i % 2 == 0 ? "JAVA" : ".Net", randomFunction.apply(9));
			data.add(dev);
		}
		return data;
	}

	static List<Dev2> data2() {
		List<Dev2> data = new ArrayList<Dev2>();
		for (int i = 1; i <= 9; i++) {
			Dev2 dev = new Dev2(i, "Dev" + i);
			dev.getSkillList().add(new Skill("JAVA", randomBiFunction.apply(0, 9)));
			dev.getSkillList().add(new Skill(".Net", randomSupplier.get()));
			data.add(dev);
		}
		return data;
	}

	public static void main(String[] args) {

		// exercise1();
		// exercise2();
		// exercise3();
		// exercise4();
		// exercise5();

		// exercise1_1();
		// exercise2_1();
		// exercise3_1();
		// exercise4_1();
		// exercise5_1();

	}

	static void exercise1() {

		// get devs who has Java Skill and >= 3 exp
		List<Dev> devs = data();
		devs = devs.stream().filter(dev -> "JAVA".equals(dev.getSkill()) && dev.getExp() >= 3)
				.collect(Collectors.toList());
		devs.forEach(devDetail);

	}

	static void exercise1_1() {

		// get devs who has Java Skill and >= 3 exp
		List<Dev2> devs = data2();
		devs = devs.stream()
				.filter(dev -> dev.getSkillList().stream()
						.anyMatch(skill -> "JAVA".equals(skill.getSkill()) && skill.getExp() >= 3))
				.collect(Collectors.toList());
		devs.forEach(devDetail2);

	}

	static void exercise2() {
		// get top 5-10 devs who has Java Skill and >= 3 exp order by exp asc
		List<Dev> devs = data();
		// code script
		devs = devs.stream().filter(dev -> "JAVA".equals(dev.getSkill()) && dev.getExp() >= 3).sorted((dev1, dev2) -> {
			return Integer.compare(dev1.getExp(), dev2.getExp());
		}).skip(5).limit(5).collect(Collectors.toList());
		devs.forEach(devDetail);
	}

	static void exercise2_1() {

		// get top 5 devs who has Java Skill and >= 3 exp order by exp desc
		List<Dev2> devs = data2();
		devs = devs.stream().filter(dev -> dev.getSkillList().stream().anyMatch(skillJava3Year))
				.sorted((dev1, dev2) -> {
					return Integer.compare(
							dev2.getSkillList().stream().filter(skillJava3Year).findFirst().get().getExp(),
							dev1.getSkillList().stream().filter(skillJava3Year).findFirst().get().getExp());
				}).limit(5).collect(Collectors.toList());
		devs.forEach(devDetail2);

	}

	static void exercise3() {
		// list out highest, lowest, average experience of devs
		int highestExp = 0;
		int lowestExp = 0;
		double averageExp = 0;

		List<Dev> devs = data();
		IntSummaryStatistics stats = devs.stream().mapToInt(dev -> dev.getExp()).summaryStatistics();

		highestExp = stats.getMax();
		lowestExp = stats.getMin();
		averageExp = stats.getAverage();

		System.out.println("Highest exp in devs : " + highestExp);
		System.out.println("Lowest exp in devs : " + lowestExp);
		System.out.println("Average exp of all devs : " + averageExp);
	}

	static void exercise3_1() {

		// list out highest, lowest, average experience of devs
		int highestExp = 0;
		int lowestExp = 0;
		double averageExp = 0;

		List<Dev2> devs = data2();

		devs.forEach(devDetail2);
		IntSummaryStatistics stats = devs.stream().mapToInt(expJavaDev).summaryStatistics();

		highestExp = stats.getMax();
		lowestExp = stats.getMin();
		averageExp = stats.getAverage();

		System.out.println("Highest exp in devs : " + highestExp);
		System.out.println("Lowest exp in devs : " + lowestExp);
		System.out.println("Average exp of all devs : " + averageExp);
	}

	static void exercise4() {
		List<Dev> devs = data();
		boolean hasExp = false;
		boolean exp5 = false;
		boolean noneExp = false;

		hasExp = devs.stream().parallel().allMatch(dev -> dev.getExp() >= 0);
		System.out.println("All devs has exp : " + hasExp);

		exp5 = devs.stream().parallel().anyMatch(dev -> dev.getExp() >= 5);
		System.out.println("Any dev has exp >= 5 : " + exp5);

		noneExp = devs.stream().parallel().noneMatch(dev -> dev.getExp() != 0);
		System.out.println("All devs has none exp : " + noneExp);

	}

	static void exercise4_1() {

		List<Dev2> devs = data2();

		boolean hasExp = false;
		boolean exp5 = false;
		boolean noneExp = false;

		hasExp = devs.stream().parallel()
				.allMatch(dev -> dev.getSkillList().stream().anyMatch(skill -> skill.getExp() > 0));

		exp5 = devs.stream().parallel()
				.anyMatch(dev -> dev.getSkillList().stream().anyMatch(skill -> skill.getExp() >= 5));

		noneExp = devs.stream().parallel()
				.noneMatch(dev -> dev.getSkillList().stream().allMatch(skill -> skill.getExp() > 0));
		System.out.println("All devs has exp : " + hasExp);

		System.out.println("Any dev has exp >= 5 : " + exp5);

		System.out.println("All devs has none exp : " + noneExp);
	}

	static void exampleConvertDataType() {
		List<Dev> devs = data();

		// convert list devs to set devs
		Set<Dev> setDevs = devs.stream().parallel().filter(dev -> true).collect(Collectors.toSet());

		// convert list devs to list devs
		List<Dev> listDevs = devs.stream().parallel().filter(dev -> true).collect(Collectors.toList());

		// convert list devs to map devs with key = dev's id, value = dev object

		// Map<Integer, Dev> mapDevs = devs.stream().parallel().filter(dev ->
		// true).collect(Collectors.toMap(dev -> dev.getId(), dev ->dev));
		// mapDevs.values().forEach(devDetail);

		// Map<Integer, Dev> mapDevs = devs.stream().parallel().filter(dev ->
		// true).collect(Collectors.toMap(Dev::getId, Function.identity()));
		// mapDevs.values().forEach(devDetail);

		// convert list devs to map devs with key = number exp, value = list dev
		Map<Integer, List<Dev>> mapExpDevs = devs.stream().parallel().collect(Collectors.groupingBy(Dev::getExp));
		mapExpDevs.forEach((key, value) -> System.out.println("Exp " + key + " total:" + value.size()));
	}

	static void exercise5() {
		List<Dev> devs = data();

		// list out numbers of dev per skill
		Map<String, Long> numbersPerSkill = devs.stream()
				.collect(Collectors.groupingBy(Dev::getSkill, Collectors.counting()));
		numbersPerSkill.forEach((key, value) -> System.out.println("Skill: " + key + " total:" + value));

		// list out average experience per skill
		Map<String, Double> averageExp = devs.stream()
				.collect(Collectors.groupingBy(Dev::getSkill, Collectors.averagingDouble(Dev::getExp)));
		averageExp.forEach((key, value) -> System.out.println("Skill: " + key + " average exp:" + value));

		// list out experience number and numbers dev
		Map<Integer, Long> statisticExp = devs.stream()
				.collect(Collectors.groupingBy(Dev::getExp, Collectors.counting()));
		statisticExp.forEach((key, value) -> System.out.println("Exp: " + key + " total:" + value));

		// list out numbers dev per skill
		Map<String, Long> statisticExpAndSkill = devs.stream().collect(
				Collectors.groupingBy(dev -> dev.getSkill() + " with " + dev.getExp() + " exp", Collectors.counting()));
		statisticExpAndSkill.forEach((key, value) -> System.out.println("Dev " + key + " total:" + value));

		// list out highest exp dev per skill
		Map<String, Optional<Dev>> statisticSkillAndMaxExp = devs.stream().collect(
				Collectors.groupingBy(dev -> dev.getSkill(), Collectors.maxBy(Comparator.comparing(Dev::getExp))));
		statisticSkillAndMaxExp.forEach((key, value) -> System.out
				.println(key + ": " + value.get().getName() + " with " + value.get().getExp() + " exp"));

		// list out numbers of Senior, Junior. Senior must be > 5 exp
		Map<Boolean, Long> statisticPartion = devs.stream()
				.collect(Collectors.partitioningBy(dev -> dev.getExp() > 5, Collectors.counting()));
		statisticPartion.forEach((key, value) -> System.out.println((key ? "Senior: " : "Junior: ") + value));

	}

	static void exercise5_1() {

		List<Dev2> devs = data2();
		devs.forEach(devDetail2);
		// list out numbers of dev per skill
		Map<String, Long> numbersPerSkill = devs.stream().flatMap(dev -> dev.getSkillList().stream())
				.collect(Collectors.groupingBy(Skill::getSkill, Collectors.counting()));

		numbersPerSkill.forEach((key, value) -> System.out.println("Skill: " + key + " total:" + value));
		// list out average experience per skill
		Map<String, Double> averageExp = devs.stream().flatMap(dev -> dev.getSkillList().stream())
				.collect(Collectors.groupingBy(Skill::getSkill, Collectors.averagingDouble(Skill::getExp)));
		averageExp.forEach((key, value) -> System.out.println("Skill: " + key + " average exp:" + value));
		// list out experience number and numbers dev
		Map<Integer, Long> statisticExp = devs.stream().flatMap(dev -> dev.getSkillList().stream())
				.collect(Collectors.groupingBy(Skill::getExp, Collectors.counting()));
		statisticExp.forEach((key, value) -> System.out.println("Exp: " + key + " total:" + value));
		// list out numbers dev per skill
		Map<String, Long> statisticExpAndSkill = devs.stream().flatMap(dev -> dev.getSkillList().stream())
				.collect(Collectors.groupingBy(skill -> skill.getSkill() + " with " + skill.getExp() + " exp",
						Collectors.counting()));
		statisticExpAndSkill.forEach((key, value) -> System.out.println("Dev " + key + " total:" + value));
		// list out highest exp dev per skill
		Map<String, Optional<Skill>> statisticSkillAndMaxExp = devs.stream().flatMap(dev -> dev.getSkillList().stream()).collect(
				Collectors.groupingBy(skill -> skill.getSkill(), Collectors.maxBy(Comparator.comparing(Skill::getExp))));
		statisticSkillAndMaxExp.forEach((key, value) -> System.out
				.println(key + ": " + value.get().getSkill() + " with " + value.get().getExp() + " exp"));
		// list out numbers of Senior, Junior. Senior must be > 5 exp
		Map<Boolean, Long> statisticPartion = devs.stream().flatMap(dev -> dev.getSkillList().stream())
				.collect(Collectors.partitioningBy(skill -> skill.getExp() > 5, Collectors.counting()));
		statisticPartion.forEach((key, value) -> System.out.println((key ? "Senior: " : "Junior: ") + value));
	}
}
