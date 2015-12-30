#!/usr/bin/php
<?php

/*
// flights

// POZOR!!! treba zmenit "path" na "flight" v print_path()

$from = array("NewYork", "Boston", "LasVegas", "NewYork", "Praha", "NewYork", "Praha", "Pariz", "Pariz", "NewYork");
$to   = array("Boston", "NewYork", "NewYork", "LasVegas", "NewYork", "Praha", "Pariz", "Praha", "NewYork", "Pariz");
*/

/*
// region 1
$from = array("Praha", "Plzen", "Praha", "Liberec", "Liberec", "Kladno");
$to   = array("Plzen", "Praha", "Liberec", "Praha", "Kladno", "Liberec");
*/
/*
// region 2
$from = array("NewYork", "Trenton", "NewYork", "Stamford", "Trenton", "Lakewood");
$to   = array("Trenton", "NewYork", "Stamford", "NewYork", "Lakewood", "Trenton");
*/
/*
// region 3
$from = array("Waymouth", "Newton", "Newton", "Boston", "Waymouth", "Boston");
$to   = array("Newton", "Waymouth", "Boston", "Newton", "Boston", "Waymouth");
*/
/*
// region 4
$from = array("Prescott", "Flagstaff", "Flagstaff", "LasVegas", "LasVegas", "Henderson");
$to   = array("Flagstaff", "Prescott", "LasVegas", "Flagstaff", "Henderson", "LasVegas");
*/
/*
// region 5
$from = array("Paris", "Vernon", "Vernon", "Dreux", "Paris", "Etampes", "Etampes", "Fontainebleau");
$to   = array("Vernon", "Paris", "Dreux", "Vernon", "Etampes", "Paris", "Fontainebleau", "Etampes");
*/

$nodes = array_unique($from);
$output = array();

foreach($nodes as $node1) {
	foreach($nodes as $node2) {
		if($node1 != $node2) {
			$output = array_merge($output, print_path($node1, $node2));
		}
	}
}
$output = array_unique($output);
foreach($output as $out) {
	echo $out;
}

function print_path($node1, $node2) {
	$path = find_path($node1, $node2, array($node1));
	$output = array();
	$prevNode = $node1;
	foreach($path as $node) {
		if($node == $node1) continue;
		
		array_push($output, "(path $prevNode $node2 $node)\n");
		$prevNode = $node;
	}
	return $output;
}

function find_path($A, $B, $closed) {
	global $from, $to;
	
	if($A == $B) {
		return array($B);
	}
	
	$findsResult = false;
	foreach($from as $key=>$node) {
		if($node == $A) {
			$nodeTo = $to[$key];
			if(!in_array($nodeTo, $closed)) {
				array_push($closed, $nodeTo);
				$path = find_path($nodeTo, $B, $closed);
				if($path !== false) {
					$findsResult = true;
					array_unshift($path, $A);
					return $path;
				}
			}
		}
	}
	
	if(!$findsResult) return false;
}

