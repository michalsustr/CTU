%% Show data
load data_nonlinear.mat;
figure, hold on;
ppatterns(X,y);

%% Transformation of training data
X = [X(1,:); X(2,:); X(1,:).^2; X(1,:).*X(2,:); X(2,:).^2; ones(1, size(X,2))];


%% Perceptron implementation
w = 0.5*zeros(size(X,1),1);
pb_model.fun = 'perceptron_nonlinear_show_boundary';

for iteration = 1 : 100          % prevent never-ending loops
  for ii = 1 : size(X,2)         % cycle through training set
    if sign(w'*X(:,ii)) ~= y(ii) % wrong decision?
      w = w + X(:,ii) * y(ii);   % then add (or subtract) this point to w
      
      pb_model.w = w;
      h=pboundary(pb_model);
      pause(0.05);
      set(h,'visible','off');
    end
  end
  
  error=sum(sign(w'*X)~=y)/size(X,2);  % show misclassification rate
  if(error == 0)
      break
  end
end

pboundary(pb_model);
