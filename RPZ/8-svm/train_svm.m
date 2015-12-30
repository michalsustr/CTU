load data_33rpz_cv08


%% Calculate measurements x and y
left_half  = 1:size(images,2)/2; % left half indices
right_half = size(images,2)/2+1:size(images,2);
x=sum(sum(images(:, left_half, :))) - sum(sum(images(:, right_half, :)));
x=permute(x, [3 2 1]); % rearrange to get a column vector
x=(x-min(x)) ./ (max(x)-min(x));

upper_half  = 1:size(images,1)/2; % upper half indices
lower_half = size(images,1)/2+1:size(images,1);
y=sum(sum(images(upper_half, :, :))) - sum(sum(images(lower_half, :, :)));
y=permute(y, [3 2 1]); % rearrange to get a column vector
y=(y-min(y)) ./ (max(y)-min(y));

X=[x'; y'];
y=labels;
y(y==2)=-1;

data.X = X; data.y = y;
options.ker = 'rbf'; options.arg = 0.4; options.C = 10000;
model=show_nonlinear_svm(data, options);

classes.old = y;
classes.new = svm_nonlinear_show_boundary(X, model);
error = sum(classes.new ~= classes.old)/length(classes.new)

%% Cross-validation

options.ker = 'rbf'; options.C = 10000;
sigma=[0.1:0.2:2 2:10:100];
errors=zeros(10,length(sigma));
for i = 1:10
    if(i ~= 10)
        itst=(i-1)*floor(length(X)/10)+1:i*floor(length(X)/10);
    else
        itst=(i-1)*floor(length(X)/10)+1:length(X);
    end
    itrn=1:length(X);
    itrn(itst) = [];
 
    for j=1:length(sigma)
        data.X = X(:,itrn); data.y = y(itrn);
        options.arg = sigma(j);
        model=svmboundary(data, options);
        % test error
        classes=svm_nonlinear_show_boundary(X(:,itst), model);
        errors(i,j)=sum(y(itst) ~= classes)/length(itst);
    end
end

% find minimal error
[~,j]=min(sum(errors));
sigma(j)
