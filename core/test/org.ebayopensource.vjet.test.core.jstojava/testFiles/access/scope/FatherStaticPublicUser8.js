vjo.ctype('access.scope.FatherStaticPublicUser8')
.needs('access.scope.publicModifier.PublicPerson1')
.props({
	//> public void main(String[] args)
	main : function(args)
	{
		vjo.sysout.println(this.vj$.PublicPerson1.getX());
		vjo.sysout.println(this.vj$.PublicPerson1.x);
	}
})
.endType();