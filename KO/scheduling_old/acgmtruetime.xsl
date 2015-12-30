<?xml version="1.0" encoding="UTF-8"?>
<!-- generate code function for Matlab/Simulink-based simulator for real-time control systems TrueTime   -->
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" >
                                             
       
      <xsl:output method="text" encoding="UTF-8"/>

      <xsl:template match="matlabdata">
             <xsl:text>function [exectime,data] = code(seg,data) </xsl:text>
             
             <xsl:text>&#10;</xsl:text>
             <xsl:text>% &#10;</xsl:text>
             <xsl:text>% This document was automatically generated by Code Generator for Scheduling toolbox </xsl:text>             
             <xsl:text>&#10;</xsl:text>
             <xsl:text>% &#10;</xsl:text>
             <xsl:text>&#10;</xsl:text>
             
             <xsl:text> i=floor(ttCurrentTime/ttGetPeriod); &#10;</xsl:text> 
             <xsl:text> &#10;</xsl:text> 
             <xsl:text>switch(seg) &#10;</xsl:text>    
             
             <xsl:call-template name="GenCase">
                    <xsl:with-param name="End" select="/matlabdata/taskset/task/schedule/period"></xsl:with-param>
                    <xsl:with-param name="Counter" select="1"></xsl:with-param>
             </xsl:call-template>
             <xsl:text>&#10;</xsl:text>
             <xsl:call-template name="WritePipeUnitFunction"></xsl:call-template>
             <xsl:call-template name="WriteFunctions"/>  
      </xsl:template>
     
       <xsl:template name="GenCase">
              <xsl:param name="End"></xsl:param>
              <xsl:param name="Counter"></xsl:param>
              <xsl:choose>
                     <xsl:when test="$End >= $Counter">
                            <xsl:text>case </xsl:text>
                            <xsl:value-of select="$Counter"/>
                            <xsl:text>&#10;</xsl:text>
                           
                            <xsl:call-template name="WriteTasks">
                                   <xsl:with-param name="Counter" select="$Counter - 1"></xsl:with-param>
                            </xsl:call-template>
                                                    
                            <xsl:text>       data.units=pipeunit(data.units);</xsl:text>
                            <xsl:text>&#10;</xsl:text>
                            <xsl:text>       exectime = 1/data.frequency;</xsl:text>
                            <xsl:text>&#10;</xsl:text>
                            
                            <xsl:call-template name="GenCase">
                                   <xsl:with-param name="End" select="$End"></xsl:with-param>
                                   <xsl:with-param name="Counter" select="$Counter + 1"></xsl:with-param>
                            </xsl:call-template>
                     </xsl:when>
                     <xsl:otherwise>
                            <xsl:text>case </xsl:text>
                            <xsl:value-of select="$End+1"/>
                            <xsl:text>&#10;</xsl:text>
                            <xsl:text>       exectime = -1;&#10;</xsl:text>
                            <xsl:text>end &#10;</xsl:text>               
                     </xsl:otherwise>
              </xsl:choose>
              
       </xsl:template>
       
   
       <xsl:template name="WriteTasks">
              <xsl:param name="Counter"></xsl:param>              
              <xsl:for-each select="/matlabdata/taskset/task/schedule">
                     <xsl:variable name="Processor" select="item/processor"></xsl:variable>
                     <xsl:variable name="Latency" select="/matlabdata/taskset/tsuserparam/struct/struct[@name='CodeGenerationData']/struct[@name='Processors']/struct[@y=$Processor]/double[@name='Latency']/item"></xsl:variable>
                     <xsl:if test="((item/start  +$Latency) mod  period)  = $Counter">
                            <xsl:choose>
                                   <xsl:when test="ancestor::task/descendant::userparam/struct/struct/char[@name='TaskOutputIdentifier']=/matlabdata/taskset/tsuserparam/struct/struct[@name='CodeGenerationData']/struct[@name='Variables']/struct[char='output'][char/@name='Type']/char[@name='Name']">                                            
                                          <xsl:text>       </xsl:text> 
                                          <xsl:call-template name="FindVariable">
                                                 <xsl:with-param name="MatlabName" select="ancestor::task/descendant::userparam/struct/struct/char[@name='TaskOutputIdentifier']"/>
                                          </xsl:call-template>
                                          <xsl:text> = </xsl:text>
                                          <xsl:text>data.units.unit</xsl:text>
                                          <xsl:value-of select="$Processor"></xsl:value-of>
                                          <xsl:text>(</xsl:text>     
                                          <xsl:value-of select="$Latency +1"></xsl:value-of>
                                          <xsl:text>);</xsl:text>                                          
                                          <xsl:text> %</xsl:text>
                                          <xsl:value-of select="ancestor::task/name"/>
                                          <xsl:text> Out</xsl:text>                                         
                                          <xsl:text>&#10;</xsl:text>
                                          <xsl:text>       ttAnalogOut(</xsl:text>
                                          <xsl:call-template name="OutputChanel">
                                                 <xsl:with-param name="MatlabName" select="ancestor::task/descendant::userparam/struct/struct/char[@name='TaskOutputIdentifier']"/>
                                          </xsl:call-template>
                                          <xsl:text>,</xsl:text>
                                          <xsl:text>data.units.unit</xsl:text>
                                          <xsl:value-of select="$Processor"></xsl:value-of>
                                          <xsl:text>(</xsl:text>     
                                          <xsl:value-of select="$Latency +1"></xsl:value-of>
                                          <xsl:text>)</xsl:text>   
                                          <xsl:text>);</xsl:text>
                                          <xsl:text>&#10;</xsl:text>
                                       </xsl:when>
                                   <xsl:otherwise>
                                          <xsl:text>       </xsl:text>
                                          <xsl:call-template name="FindVariable">
                                                 <xsl:with-param name="MatlabName" select="ancestor::task/descendant::userparam/struct/struct/char[@name='TaskOutputIdentifier']"/>
                                          </xsl:call-template>
                                          <xsl:text> = </xsl:text>                                         
                                          <xsl:text>data.units.unit</xsl:text>
                                          <xsl:value-of select="$Processor"></xsl:value-of>
                                          <xsl:text>(</xsl:text>     
                                          <xsl:value-of select="$Latency +1"></xsl:value-of>
                                          <xsl:text>) ;</xsl:text>                                          
                                          <xsl:text> %</xsl:text>
                                          <xsl:value-of select="ancestor::task/name"/>
                                          <xsl:text> Out&#10;</xsl:text>
                                   </xsl:otherwise>
                            </xsl:choose> 
                     </xsl:if>
                    
              </xsl:for-each>
              
              <xsl:for-each select="/matlabdata/taskset/task/schedule">
                     <xsl:variable name="Processor" select="item/processor"></xsl:variable>
                     <xsl:variable name="Latency" select="/matlabdata/taskset/tsuserparam/struct/struct[@name='CodeGenerationData']/struct[@name='Processors']/struct[@y=$Processor]/double[@name='Latency']/item"></xsl:variable>
                      <xsl:if test="(item/start mod  period)  = $Counter">
                            <xsl:if test="(item/start div  period) >= 1">
                                   <xsl:text>       if i>= </xsl:text>
                                   <xsl:value-of select="floor(item/start div period)"/>
                                   <xsl:text>&#10;</xsl:text>
                            </xsl:if>
                            <xsl:text>       </xsl:text>
                            <xsl:text>data.units.unit</xsl:text>
                            <xsl:value-of select="$Processor"></xsl:value-of>
                            <xsl:text>(1) = </xsl:text>                                          
                            <xsl:call-template name="TaskRHS"></xsl:call-template>
                            <xsl:text>;</xsl:text>
                            <xsl:text> %</xsl:text>
                            <xsl:value-of select="ancestor::task/name"/>
                            <xsl:text>&#10;</xsl:text>
                            <xsl:if test="(item/start div  period) >= 1">
                                   <xsl:text>       end</xsl:text>
                                   <xsl:text>&#10;</xsl:text>
                            </xsl:if>
                     </xsl:if>
              </xsl:for-each>
              
       </xsl:template>
       
       <!-- write function  PipeUnits  -->
       <xsl:template name="WritePipeUnitFunction">
              
              <xsl:text>function u=pipeunit(u)</xsl:text>
              <xsl:text>&#10;</xsl:text>
              <xsl:for-each select="/matlabdata/taskset/tsuserparam/struct/struct[@name='CodeGenerationData']/struct[@name='Processors']/struct">
                     <xsl:text>        %  unit </xsl:text>
                     <xsl:value-of select="char[@name='Operator']"/>
                     <xsl:text>&#10;</xsl:text>
                     <xsl:text>       for i=1:</xsl:text>
                     <xsl:value-of select="double[@name='Latency']/item"/>
                     <xsl:text>&#10;</xsl:text>
                    
                     <xsl:text>              u.unit</xsl:text>
                     <xsl:value-of select="position()"/>
                     <xsl:text>(</xsl:text>
                     <xsl:value-of select="double[@name='Latency']/item + 2"/>
                     <xsl:text> - i) = </xsl:text>
                     <xsl:text> u.unit</xsl:text>
                     <xsl:value-of select="position()"/>
                     <xsl:text>(</xsl:text>
                     <xsl:value-of select="double[@name='Latency']/item + 1"/>
                     <xsl:text> - i); </xsl:text>
                     <xsl:text>&#10;</xsl:text>
                     <xsl:text>       end</xsl:text>
                     <xsl:text>&#10;</xsl:text>
                     <xsl:text>       u.unit</xsl:text>
                     <xsl:value-of select="position()"/>
                     <xsl:text>(1)=0;</xsl:text>
                     <xsl:text>&#10;</xsl:text>   
                     
              </xsl:for-each>
              <xsl:text>return</xsl:text>
              <xsl:text>&#10;</xsl:text>   
       </xsl:template>
       
       
       <!-- copy function  definition from source xml file  -->
       <xsl:template name="WriteFunctions">
               <xsl:choose>
                     <xsl:when test="/matlabdata/taskset/tsuserparam/struct/struct/struct[@name='Functions' and @array='true']">
                            <xsl:for-each select="/matlabdata/taskset/tsuserparam/struct/struct/struct[@name='Functions']/struct">
                                   <xsl:text>function </xsl:text>
                                   <xsl:value-of select="./char[@name='OutputIdentifier']"/>
                                   <xsl:text>=</xsl:text>
                                   <xsl:value-of select="./char[@name='Name']"/>
                                   <xsl:text>(</xsl:text>
                                   <xsl:choose>
                                          <xsl:when test="./struct[@name='Operands' and @array='true']">
                                                 <xsl:for-each select="./struct[@name='Operands']/struct/char[@name='Name']">
                                                        <xsl:value-of select="."/>
                                                        <xsl:if test="position()!=last()">
                                                               <xsl:text>,</xsl:text>
                                                        </xsl:if>
                                                 </xsl:for-each>
                                          </xsl:when>
                                          <xsl:otherwise>
                                                 <xsl:value-of select="./struct[@name='Operands']/char[@name='Name']"></xsl:value-of>
                                          </xsl:otherwise>
                                   </xsl:choose>      
                                   <xsl:text>)</xsl:text>
                                   <xsl:text>&#10;</xsl:text>    
                                   <xsl:choose>
                                          <xsl:when test="./struct[@name='Lines' and @array='true']">
                                                 <xsl:for-each select="./struct[@name='Lines']/struct/char[@name='Line']">
                                                        <xsl:value-of select="."/>
                                                        <xsl:text>&#10;</xsl:text>      
                                                 </xsl:for-each>
                                          </xsl:when>
                                          <xsl:otherwise>
                                                 <xsl:value-of select="./struct[@name='Lines']/char[@name='Line']"></xsl:value-of>
                                                 <xsl:text>&#10;</xsl:text>   
                                          </xsl:otherwise>
                                   </xsl:choose>
                                   <xsl:text>return</xsl:text>
                                   <xsl:text>&#10;</xsl:text>
                                   <xsl:text>&#10;</xsl:text>
                            </xsl:for-each>
                     </xsl:when>
                     <xsl:otherwise>
                            <xsl:for-each select="/matlabdata/taskset/tsuserparam/struct/struct/struct[@name='Functions']">
                                   <xsl:text>function </xsl:text>
                                   <xsl:value-of select="./char[@name='OutputIdentifier']"/>
                                   <xsl:text>=</xsl:text>
                                   <xsl:value-of select="./char[@name='Name']"/>
                                   <xsl:text>(</xsl:text>
                                   <xsl:choose>
                                          <xsl:when test="./struct[@name='Operands' and @array='true']">
                                                 <xsl:for-each select="./struct[@name='Operands']/struct/char[@name='Name']">
                                                        <xsl:value-of select="."/>
                                                        <xsl:if test="position()!=last()">
                                                               <xsl:text>,</xsl:text>
                                                        </xsl:if>
                                                 </xsl:for-each>
                                          </xsl:when>
                                          <xsl:otherwise>
                                                 <xsl:value-of select="./struct[@name='Operands']/char[@name='Name']"></xsl:value-of>
                                          </xsl:otherwise>
                                   </xsl:choose>      
                                   <xsl:text>)</xsl:text>
                                   <xsl:text>&#10;</xsl:text>    
                                   <xsl:choose>
                                          <xsl:when test="./struct[@name='Lines' and @array='true']">
                                                 <xsl:for-each select="./struct[@name='Lines']/struct/char[@name='Line']">
                                                        <xsl:value-of select="."/>
                                                        <xsl:text>&#10;</xsl:text>      
                                                 </xsl:for-each>
                                          </xsl:when>
                                          <xsl:otherwise>
                                                 <xsl:value-of select="./struct[@name='Lines']/char[@name='Line']"></xsl:value-of>
                                                 <xsl:text>&#10;</xsl:text>   
                                          </xsl:otherwise>
                                   </xsl:choose>
                                   <xsl:text>return</xsl:text>
                                   <xsl:text>&#10;</xsl:text>
                                   <xsl:text>&#10;</xsl:text>
                            </xsl:for-each>
                     </xsl:otherwise>
              </xsl:choose>
       </xsl:template>
       
       
       <!-- find and write right name of variables -->
       <xsl:template name="FindVariable">
              <xsl:param name="MatlabName"/>
              
              
              <xsl:for-each select="/matlabdata/taskset/tsuserparam/struct/struct/struct/struct[char[@name='Type'] = 'input']">
                     <xsl:if test="char[@name='Name']=$MatlabName">
                            <xsl:text>ttAnalogIn(</xsl:text>
                            <xsl:value-of select="position()"/>
                            <xsl:text>)</xsl:text>
                     </xsl:if>                    
                     
              </xsl:for-each>
              
              <xsl:for-each select="/matlabdata/taskset/tsuserparam/struct/struct/struct/struct[char[@name='Type'] = 'output']">      
                     <xsl:variable name="NumberOfVarialses" select="count(/matlabdata/taskset/tsuserparam/struct/struct/struct/struct[char='memory'][char/@name='Type'])"></xsl:variable>  
                     <xsl:if test="char[@name='Name']=$MatlabName">
                            <xsl:text>data.reg</xsl:text>
                            <xsl:value-of select="$NumberOfVarialses+position()"/>
                     </xsl:if>                    
                     
              </xsl:for-each>
              
              <xsl:for-each select="/matlabdata/taskset/tsuserparam/struct/struct/struct/struct[char[@name='Type'] = 'memory']">
                     <xsl:if test="char[@name='Name']=$MatlabName">
                            <xsl:text>data.reg</xsl:text> 
                            <xsl:value-of select="position()"/>
                     </xsl:if>   
                     
              </xsl:for-each>
              <xsl:for-each select="/matlabdata/taskset/tsuserparam/struct/struct/struct/struct[char[@name='Type'] = 'constant']">
                     <xsl:if test="char[@name='Name']=$MatlabName">
                            <xsl:text>data.const</xsl:text> 
                            <xsl:value-of select="position()"/>
                     </xsl:if>                    
                     
              </xsl:for-each>
              
       </xsl:template>
       
       <!-- write number of output chanel according to argument  MatlabName-->
       <xsl:template name="OutputChanel">
              <xsl:param name="MatlabName"/>  
              <xsl:for-each select="/matlabdata/taskset/tsuserparam/struct/struct/struct/struct[char[@name='Type'] = 'output']">
                     <xsl:if test="char[@name='Name']=$MatlabName">
                            <xsl:value-of select="position()"/>
                     </xsl:if>                    
              </xsl:for-each>
       </xsl:template>
       
       <!--  write right hand side operands of tasks -->
       <xsl:template name="TaskRHS">
              <xsl:choose>
                     <xsl:when test="(ancestor::task/descendant::userparam/struct/struct/char[@name='TaskOperator']=/matlabdata/taskset/tsuserparam/struct/struct[@name='CodeGenerationData']/struct[@name='Functions' and @array='true']/struct/char[@name='Name']) or (ancestor::task/descendant::userparam/struct/struct/char[@name='TaskOperator']=/matlabdata/taskset/tsuserparam/struct/struct[@name='CodeGenerationData']/struct[@name='Functions']/char[@name='Name'])">
                            
                            <xsl:value-of select="ancestor::task/descendant::userparam/struct/struct/char[@name='TaskOperator']"/>
                            <xsl:text>(</xsl:text>
                            
                            <xsl:choose>
                                   <xsl:when test="ancestor::task//descendant::userparam/struct/struct/struct[@name='Operands' and @array='true']"><!-- vice operandu -->
                                          <xsl:for-each select="ancestor::task//descendant::userparam/struct/struct/struct[@name='Operands']/struct">
                                                 <xsl:if test="char[@name='Sign']='-'">
                                                        <xsl:value-of select="char[@name='Sign']"/>
                                                 </xsl:if>                                                                                                                           
                                                 <xsl:call-template name="FindVariable">
                                                        <xsl:with-param name="MatlabName" select="char[@name='Name']"/>
                                                 </xsl:call-template>
                                                 <xsl:if test="position()!=last()">
                                                        <xsl:text>,</xsl:text>
                                                 </xsl:if>
                                          </xsl:for-each>
                                   </xsl:when>
                                   <xsl:otherwise><!-- jeden operand -->       
                                          <xsl:for-each select="ancestor::task//descendant::userparam/struct/struct/struct[@name='Operands']">
                                                 <xsl:if test="char[@name='Sign']='-'">
                                                        <xsl:value-of select="char[@name='Sign']"/>
                                                 </xsl:if>                                                                                                                           
                                                 <xsl:call-template name="FindVariable">
                                                        <xsl:with-param name="MatlabName" select="char[@name='Name']"/>
                                                 </xsl:call-template>                                                 
                                          </xsl:for-each>
                                   </xsl:otherwise>
                            </xsl:choose>
                            <xsl:text>)</xsl:text>
                     </xsl:when>
                     <xsl:otherwise>
                            
                            <xsl:choose>
                                   <xsl:when test="ancestor::task/descendant::userparam/struct/struct/char[@name='TaskOperator']='null'">
                                          <xsl:for-each select="ancestor::task//descendant::userparam/struct/struct/struct[@name='Operands']">                                                          
                                                 <xsl:if test="char[@name='Sign']='-'">
                                                        <xsl:value-of select="char[@name='Sign']"></xsl:value-of>
                                                 </xsl:if> 
                                                 <xsl:call-template name="FindVariable">
                                                        <xsl:with-param name="MatlabName" select="char[@name='Name']"/>
                                                 </xsl:call-template>                                                                                                                              
                                          </xsl:for-each> 
                                   </xsl:when>
                                   <xsl:otherwise>
                                          <xsl:for-each select="ancestor::task//descendant::userparam/struct/struct/struct[@name='Operands']/struct">                                                          
                                                 <xsl:if test="position()=last()">
                                                        <xsl:variable name="TaskOperator" select="ancestor::task/descendant::userparam/struct/struct/char[@name='TaskOperator']"></xsl:variable>
                                                        <xsl:choose>
                                                               <xsl:when test="$TaskOperator='+' and char[@name='Sign']='-'">
                                                                      <xsl:value-of select="char[@name='Sign']"/>
                                                               </xsl:when>
                                                               <xsl:otherwise>
                                                                      <xsl:value-of select="$TaskOperator"></xsl:value-of>
                                                               </xsl:otherwise>
                                                        </xsl:choose>
                                                 </xsl:if>
                                                 <xsl:call-template name="FindVariable">
                                                        <xsl:with-param name="MatlabName" select="char[@name='Name']"/>
                                                 </xsl:call-template>                                                                                                                              
                                          </xsl:for-each>   
                                   </xsl:otherwise>
                            </xsl:choose>
                     </xsl:otherwise>
              </xsl:choose>
       </xsl:template>
       
       
</xsl:stylesheet>   
       
       
       
       
       
       
       
       
       
       
       
       
       