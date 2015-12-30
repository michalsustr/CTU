model = struct('Mean',[D1.Mean D2.Mean],'Cov',[D1.Cov D2.Cov],'Prior',[D1.Prior D2.Prior]);
   figure; hold on; 
   h = pgmm(model,struct('comp_color',['r' 'g'])); 
   legend(h,'P(x)','P(x|y=1)*P(y=1)','P(x|y=2)*P(y=2)');
   [risk,eps1,eps2,interval] = bayeserr(model)
   a = axis;
   plot([interval(2) interval(2)],[a(3) a(4)],'k');
   plot([interval(3) interval(3)],[a(3) a(4)],'k');
 