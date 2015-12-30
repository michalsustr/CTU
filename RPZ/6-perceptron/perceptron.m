%% Show data
load data_nonlinear.mat;
figure, hold on;
ppatterns(X,y);

%% Perceptron implementation

X=[X; ones(1, size(X,2))];       % adjust matrix X
w = [0.5; 0.5; 0.5]; 

for iteration = 1 : 100          % prevent never-ending loops
  for ii = 1 : size(X,2)         % cycle through training set
    if sign(w'*X(:,ii)) ~= y(ii) % wrong decision?
      w = w + X(:,ii) * y(ii);   % then add (or subtract) this point to w
      
      h=pline(w(1:2), w(3));     % show the separation line for short time
      pause(0.5);
      set(h,'visible','off');
      
    end
  end
  
  error=sum(sign(w'*X)~=y)/size(X,2)  % show misclassification rate
  if(error == 0)
      break
  end
end

pline(w(1:2), w(3));             % show the final line




