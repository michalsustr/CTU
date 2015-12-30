// mimobezky
p = x1 + t1 * v1
s = x2 + t2 * v2
// (3x1) = (3x1) + (1x1) * (3x1)

// Vektor hledane pricky (v3) musi byt zaroven kolmy na obe mimobezky
[v1; v2]' * v3 = [0 ;0]
// (2x3) * (3x1) = (2x1)

// a zaroven ho musi tvorit bod z kazdy mimobezky
v3 = p - s = x1 + t1 * v1 - ( x2 + t2 * v2 )

// kdyz se to da dohromady
[v1; v2]' * ( x1 + t1 * v1 - ( x2 + t2 * v2 ) ) = [0 ; 0]
[v1; v2]' * ( x1 - x2 ) + [v1; v2]' * ( t1* v1 - t2 * v2 )  = [0 ; 0]
[v1'; v2'] * [v1 , -v2] * [t1 ; t2]  = - [v1'; v2'] * ( x1 - x2 )
// (2x3) * (3x2) * (2x1) = - (2x3) * (3x1)
[v1' * v1 , v1' * -v2 ; v2' * v1 , v2' * -v2] * [t1 ; t2] = [ v1' * ( x2 - x1 ) ; v2' * (x2 - x1) ]

// kdyz se to zapise v citelnejsim tvaru

A * t = b
t = A \ b

A = 
[
  v1' * v1 , v1' * -v2 ;
  v2' * v1 , v2' * -v2
]

b =
[
  v1' * ( x2 - x1 );
  v2' * ( x2 - x1 )
]