function [risk, eps1, eps2, interval] = bayeserror(model1, model2)
   model = struct('Mean',[model1.Mean model2.Mean],'Cov',[model1.Sigma^2 model2.Sigma^2],'Prior',[model1.Prior model2.Prior]);
   [risk,eps1,eps2,interval] = bayeserr(model);
end