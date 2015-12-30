#!/usr/bin/php
<?php
// LOAD GRAPH
$in = file("php://stdin");
$G = [];
$nodes = [];
foreach($in as $i => $line) {
	if($i == 0 && $line != "digraph nadrazi {\n") die("Bad input format, bad header\n");
	elseif($i == 0) continue;
	
	$line = trim($line);
	if($line == "")  die("Bad input format, empty lines\n");
	if($line == "}") break;
	if(!preg_match("~([a-z0-9]+) \\-\\> ([a-z0-9]+)\;~", $line, $parts)) die("Bad input format, bad formatting of node edges\n");
	$from = $parts[1];
	$to = $parts[2];
	
	if(!isset($nodes[$from])) {
		$nodes[$from] = count($nodes);
	}
	if(!isset($nodes[$to])) {
		$nodes[$to] = count($nodes);
	}	
	$idxFrom = $nodes[$from];
	$idxTo = $nodes[$to];
	
	$G[$idxFrom][$idxTo] = 1;
}
for($i = 0; $i < count($nodes); $i++) {
	for($j = 0; $j < count($nodes); $j++) {
		if(!isset($G[$i][$j])) $G[$i][$j] = 0;
	}
}
print "Input nodes\n";
print_r($nodes);
print "-----------------------\n";
print "Input graph\n";
for($i = 0; $i < count($nodes); $i++) {
	for($j = 0; $j < count($nodes); $j++) {
		print $G[$i][$j]." ";
	}
	print "\n";
}

// Find all IN and OUT stations
// IN station = one edge out, no edge in
// OUT station = 1+ edge(s) in, no edge out
$IN=[];
$OUT=[];
for($i = 0; $i < count($nodes); $i++) {
	$edgecntout=0;
	$edgecntin=0;
	for($j = 0; $j < count($nodes); $j++) {
		if($G[$i][$j]) $edgecntout++;
	}
	
	for($j = 0; $j < count($nodes); $j++) {
		if($G[$j][$i]) $edgecntin++;
	}
	if($edgecntout == 1 && $edgecntin == 0) {
		$IN[] = $i;	
	}
	if($edgecntout == 0 && $edgecntin >= 1) {
		$OUT[] = $i;	
	}
}
print "-----------------------\n";
print "IN stations\n";
print_r($IN);
print "OUT stations\n";
print_r($OUT);

// FIND ALL PAIRS OF SHORTEST PATHS (Floyd-Warshall alg.)
include "fw.php";
$fw = new FloydWarshall($G);

$paths = [];
$MAX_PATH_LENGTH = 0;
for($i = 0; $i < count($IN); $i++) {
for($j = 0; $j < count($OUT); $j++) {
	$path = ($fw->get_path($IN[$i], $OUT[$j]));
	if(!empty($path)) {
		$paths[] = $path;
		if(count($path)+1 > $MAX_PATH_LENGTH) $MAX_PATH_LENGTH=count($path)+1;
	}
}
}

$NODE_CNT = count($nodes);
$PATHS_CNT = count($paths);
print "-----------------------\n";
print "FOUND $PATHS_CNT PATHS\n";
print_r($paths);

// FIND COMPATIBLE_PATHS
$C=[];
for($i = 0; $i < $PATHS_CNT; $i++) {
for($j = 0; $j < $PATHS_CNT; $j++) {
	$C[$i][$j] = 0;
	if(empty(array_intersect($paths[$i], $paths[$j]))) $C[$i][$j] = 1;
}}

print "-----------------------\n";
print "COMPATIBLE PATHS\n";
for($i = 0; $i < $PATHS_CNT; $i++) {
	for($j = 0; $j < $PATHS_CNT; $j++) {
		print $C[$i][$j]." ";
	}
	print "\n";
}

// FIND PATHS THAT START IN INs
$IN_paths=[];
for($i = 0; $i < $PATHS_CNT; $i++) {
	$IN_paths[$paths[$i][0]][] = $i;
}
print "-----------------------\n";
print "PATHS THAT START IN INs\n";
print_r($IN_paths);

// GENERATE UPPAAL FILE BY SECTIONS

// A)
ob_start();
print "const int PATHS_CNT = $PATHS_CNT; // number of ways how you can get from IN to OUT for given instance of problem
const int NODE_CNT = $NODE_CNT;  // number of nodes (for given instance of problem)
const int MAX_PATH_LENGTH = $MAX_PATH_LENGTH; // maximum length of a train path - aligned by the longest path and +1
";
$GEN_A=ob_get_clean();

// B)
ob_start();
print "const int paths[PATHS_CNT][MAX_PATH_LENGTH] = {\n";

for($i = 0; $i < $PATHS_CNT; $i++) {
	print "\t{";
	for($j = 0; $j < $MAX_PATH_LENGTH; $j++) {
		if($j != 0) print ", ";
		if(!isset($paths[$i][$j])) print "-1";
		else print $paths[$i][$j];
	}
	print "}";
	if($i < $PATHS_CNT-1) print ",";
	print "\n";
}
print "};\n";
$GEN_B=ob_get_clean();

// C)

ob_start();
foreach($IN_paths as $in => $paths_idx) {
	print "	if(in == $in)
		return (";
	foreach ($paths_idx as $x => $path_idx) {
		if($x > 0) print "\n\t\t     || ";
		// print self path
		print "(start[$path_idx] ";
		for ($i=0; $i < $PATHS_CNT; $i++) { 
		 	print(" &amp;&amp; !going[$i]");
		 }
		 print ")";
		// print non-collision paths
		for ($i=0; $i < $PATHS_CNT; $i++) { 
		 	if(!$C[$path_idx][$i]) continue;
		 	print "\n\t\t     || (start[$path_idx]";
		 	for ($j=0; $j < $PATHS_CNT; $j++) { 
		 		if($j == $i) print(" &amp;&amp; going[$j]");
		 		else print(" &amp;&amp; !going[$j]");
		 	}
		 	print ")";
		}
	}

	print ");
";
}
$GEN_C=ob_get_clean();


// D)

ob_start();
for($i = 0; $i < $NODE_CNT; $i++) {
	print "n$i=Node(enter[$i], leave[$i], switched[$i], critical, internal_error);\n";
}
print "\n";
for($i = 0; $i < $PATHS_CNT; $i++) {
	print
"train$i=Train(
	pending[$i],start[$i],going[$i], advance[$i], open[".$paths[$i][0]."],  free[".$paths[$i][0]."], 
	conn, switched,  enter,  leave,  internal_error, 
	0, paths[$i]);
trainWill$i=TrainWill(advance[$i], going[$i]);
trainGenerator$i=TrainGenerator(free[".$paths[$i][0]."], pending[$i]);\n\n";
}


print "cs=ControlSystem(open);
err=Errors(critical,internal_error);
";

print "system cs,err,\n\t";
for($i = 0; $i < $NODE_CNT; $i++) {
	print "n$i,";
}
print "\n";
for($i = 0; $i < $PATHS_CNT; $i++) {
	print "\ttrain$i, trainWill$i, trainGenerator$i";
	if($i+1 < $PATHS_CNT) {
		print ",";
	}
	print "\n";
}
print ";\n";

$GEN_D=ob_get_clean();

ob_start();
include "gen_template.php";
file_put_contents("generated.xml", ob_get_clean());

print "\n----------------------\n";
print "File generated.\n";

