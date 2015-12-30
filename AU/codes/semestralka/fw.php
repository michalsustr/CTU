<?php

/**
* @package FloydWarshall
* @author Janne Mikkonen <janne dot mikkonen at julmajanne dot com>
* @date $Date: 2013/03/23 05:10:48 $
* @version $Revision: 1.1.1 $
* @license GNU General Public License, version 2 http://www.opensource.org/licenses/GPL-2.0
* 
* Updated by Michal Sustr
**/

error_reporting(E_STRICT);
define('INFINITE', pow(2, (20 * 8 - 2)-1));

class FloydWarshall {

    /**
    * Distances array
    * @var array
    */
    private $dist = array(array());
    /**
    * Next array
    * @var array
    */
    private $next = array(array());
    /**
    * Weights array
    * @var array
    */
    private $weights;
    /**
    * Number of nodes
    * @var integer
    */
    private $nodes;
    /**
    * Node names array
    * @var array
    */
    private $nodenames;
    /**
    * Temporary table for various stuff.
    * @var array
    */
    private $tmp = array();

    /**
    * Constructor
    * @param array $graph Graph matrice.
    * @param array $nodenames Node names as an array.
    */
    public function __construct($graph, $nodenames='') {
        $this->weights = $graph;
        $this->nodes   = count($this->weights);
        if ( ! empty($nodenames) && $this->nodes == count($nodenames) ) {
            $this->nodenames = $nodenames;
        }
        $this->__floydwarshall();
    }

    /**
    * The actual PHP implementation of Floyd-Warshall algorithm.
    * @return void
    */
    private function __floydwarshall () {

        // Initialization
        for ( $i = 0; $i < $this->nodes; $i++ ) {
            for ( $j = 0; $j < $this->nodes; $j++ ) {
            	$this->next[$i][$j] = null;
				 if ( $this->weights[$i][$j] > 0 ) {
                    $this->dist[$i][$j] = $this->weights[$i][$j];
                    $this->next[$i][$j] = $j;
                } else {
                    $this->dist[$i][$j] = INFINITE;
                }
            }
        }
        
        // Algorithm

        for ( $k = 0; $k < $this->nodes; $k++ ) {
            for ( $i = 0; $i < $this->nodes; $i++ ) {
                for ( $j = 0; $j < $this->nodes; $j++ ) {
                    if ($this->dist[$i][$j] > ($this->dist[$i][$k] + $this->dist[$k][$j])) {
                        $this->dist[$i][$j] = $this->dist[$i][$k] + $this->dist[$k][$j];
                        $this->next[$i][$j] = $this->next[$i][$k];
                    }
                }
            }
        }
    }
    
    /**
    * Public function to access get path information.
    *
    * @param ingeger $i Starting node.
    * @param integer $j End node.
    * @return array Return array of nodes.
    */
    public function get_path($i, $j) {
		if($this->next[$i][$j] === null)
			return [];
		
		$path = [$i];
		while ($i != $j) {
			$i = $this->next[$i][$j];
			$path[] = $i;
		}
		return $path;
    }
} // End of class
?>
